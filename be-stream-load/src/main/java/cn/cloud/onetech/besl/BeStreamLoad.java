package cn.cloud.onetech.besl;

import cn.cloud.onetech.besl.conf.DorisConf;
import cn.cloud.onetech.besl.exception.DorisBatchLoadException;
import cn.cloud.onetech.besl.http.HttpPutBuilder;
import cn.cloud.onetech.besl.http.RespContent;
import cn.cloud.onetech.besl.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static cn.cloud.onetech.besl.constants.LoadStatus.PUBLISH_TIMEOUT;
import static cn.cloud.onetech.besl.constants.LoadStatus.SUCCESS;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/25 15:24
 */
public class BeStreamLoad {

    private static HttpClientBuilder httpClientBuilder = HttpUtils.getHttpClientBuilderForBatch();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<String> DORIS_SUCCESS_STATUS = new ArrayList<>(Arrays.asList(SUCCESS, PUBLISH_TIMEOUT));
    private static final Calendar calendar = Calendar.getInstance();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static DorisConf dorisConf = null;


    private static ThreadPoolExecutor executor = null;

    public static void main(String[] args) {
        loadDorisConf();
        Integer parallelism = Integer.parseInt(System.getProperty("parallelism", "10"));
        initExecutor(parallelism);
        runExecutor(parallelism);
    }

    private static void loadDorisConf() {
        dorisConf = new DorisConf(
                System.getProperty("beHost"),
                System.getProperty("beHttpPort"),
                System.getProperty("username"),
                System.getProperty("password"),
                System.getProperty("db"),
                System.getProperty("table"));
    }

    public static <T> List<List<T>> splitList(List<T> list, int subSize) {
        List<List<T>> subLists = new ArrayList<>();
        int listSize = list.size();

        // 计算总共可以切分成多少个subList
        int numSubLists = (int) Math.ceil((double) listSize / subSize);

        for (int i = 0; i < numSubLists; i++) {
            int start = i * subSize;
            int end = Math.min(start + subSize, listSize);
            subLists.add(list.subList(start, end));
        }

        return subLists;
    }

    private static void runExecutor(Integer parallelism) {
        String dataDir = "../data";
        File data = new File(dataDir);
        List<File> dataFiles = Arrays.asList(data.listFiles());
        int total = dataFiles.size();
        int average = total / parallelism;
        List<List<File>> lists = splitList(dataFiles, average);
        for (int i = 0; i < lists.size(); i++) {
            List<File> files = lists.get(i);
            executor.execute(() -> load(files));
        }
    }

    private static String getData(File dataFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile.getAbsolutePath()), StandardCharsets.UTF_8);
            return String.join(System.lineSeparator(), lines);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void load(List<File> files) {
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            String data = getData(file);
            doLoad(data);
        }
    }

    private static void initExecutor(Integer parallelism) {
        executor = new ThreadPoolExecutor(
                parallelism,
                parallelism * 4,
                30000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(2048)
        );
    }

    public static void doLoad(String data) {
        String label = String.format("stream_load_%s%02d%02d_%02d%02d%02d_%s",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                UUID.randomUUID().toString().replaceAll("-", ""));
        ByteArrayEntity entity =
                new ByteArrayEntity(data.getBytes(StandardCharsets.UTF_8));
        HttpPutBuilder putBuilder = new HttpPutBuilder();
        putBuilder
                .setUrl(dorisConf.beLoadUrl())
                .baseAuth(dorisConf.getUsername(), dorisConf.getPassword())
                .setLabel(label)
                .setFileFormat("csv")
                .setColumnSeparator(",")
                .addCommonHeader()
                .setEntity(entity)
                .addHiddenColumns(false);

        System.out.printf("stream load started for label %s at %s%n", label, LocalDateTime.now());
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            try (CloseableHttpResponse response = httpClient.execute(putBuilder.build())) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200 && response.getEntity() != null) {
                    String loadResult = EntityUtils.toString(response.getEntity());
                    RespContent respContent =
                            OBJECT_MAPPER.readValue(loadResult, RespContent.class);
                    if (!DORIS_SUCCESS_STATUS.contains(respContent.getStatus())) {
                        String errMsg =
                                String.format(
                                        "stream load error: %s, see more in %s",
                                        respContent.getMessage(),
                                        respContent.getErrorURL());
                        throw new DorisBatchLoadException(errMsg);
                    } else {
                        // success to load
                        JsonObject jsonObject = JsonParser.parseString(respContent.toString()).getAsJsonObject();
                        System.out.println(gson.toJson(jsonObject));
                        return;
                    }
                }
            } catch (Exception ex) {
                throw new DorisBatchLoadException("stream load error: ", ex);
            }
        } catch (IOException e) {
            throw new DorisBatchLoadException("stream load error: ", e);
        }
    }

}
