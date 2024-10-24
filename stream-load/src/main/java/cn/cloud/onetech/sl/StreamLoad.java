package cn.cloud.onetech.sl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/24 10:05
 */
public class StreamLoad {

    private static ThreadPoolExecutor executor = null;
    public static void main(String[] args) {
        String db = "doris_test";
        String table = "tbl_dk_50";
        String fenode = System.getProperty("fenode");
        String user = System.getProperty("user");
        String password = System.getProperty("password");
        String dataDir = System.getProperty("dataDir");
        Integer parallelism = Integer.parseInt(System.getProperty("parallelism", "10"));
        initExecutor(parallelism);
        DorisStreamLoader dorisStreamLoader = new DorisStreamLoader(fenode, db, table, user, password);
        runExecutor(dorisStreamLoader, parallelism, dataDir);
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

    private static void runExecutor(DorisStreamLoader dorisStreamLoader, Integer parallelism, String dataDir) {
        File data = new File(dataDir);
        List<File> dataFiles = Arrays.asList(data.listFiles());
        int total = dataFiles.size();
        int average = total / parallelism;
        List<List<File>> lists = splitList(dataFiles, average);
        for (int i = 0; i < lists.size(); i++) {
            List<File> averageDataFiles = lists.get(i);
            executor.execute(() -> streamLoad(dorisStreamLoader, averageDataFiles));
        }
    }

    private static void streamLoad(DorisStreamLoader dorisStreamLoader, List<File> files) {
        for (int i = 0; i < files.size(); i++) {
            File dataFile = files.get(i);
//            String data = getData(dataFile);
//            DorisStreamLoader.LoadResponse loadResponse = dorisStreamLoader.loadBatch(data);
//            System.out.println(loadResponse);
            Stream<String> stream = Stream.of(
                    "curl",
                    "--location-trusted",
                    "-u %s:'%s'",
                    "-H \"Expect:100-continue\"",
                    "-H \"column_separator:,\"",
                    "-T %s",
                    "-XPUT",
                    "http://%s/api/doris_test/tbl_dk_50/_stream_load"
            );
            String curlCommand = stream.collect(Collectors.joining(" "));
            String command = String.format(curlCommand, dorisStreamLoader.getUser(), dorisStreamLoader.getPasswd(), dataFile.getAbsolutePath(), dorisStreamLoader.getHostPort());
            System.out.println(ShellExecutor.execute(command));
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

    private static void initExecutor(Integer parallelism) {
        executor = new ThreadPoolExecutor(
                parallelism,
                parallelism * 4,
                30000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(512)
        );
    }



}
