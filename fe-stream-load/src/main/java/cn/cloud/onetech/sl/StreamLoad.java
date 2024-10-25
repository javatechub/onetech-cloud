package cn.cloud.onetech.sl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/24 10:05
 */
public class StreamLoad {

    private static ThreadPoolExecutor executor = null;
    private static Map<Integer, DorisStreamLoader> map = new HashMap<>(4);

    public static void main(String[] args) {
        String db = "doris_test";
        String table = "tbl_dk_50";
        String fenodes = System.getProperty("fenodes", "192.168.137.10:8035,192.168.137.11:8035,192.168.137.12:8035");
        String user = System.getProperty("user", "root");
        String password = System.getProperty("password","hegh^_^Doris618");
        String dataDir = System.getProperty("dataDir", ".");
        Integer parallelism = Integer.parseInt(System.getProperty("parallelism", "10"));
        initExecutor(parallelism);
        List<String> fenodeList = Arrays.asList(fenodes.split(","));
        for (int i = 0; i < fenodeList.size(); i++) {
            String fenode = fenodeList.get(i);
            DorisStreamLoader dorisStreamLoader = new DorisStreamLoader(fenode, db, table, user, password);
            map.put(i, dorisStreamLoader);
        }
        runExecutor(parallelism, dataDir);
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

    private static void runExecutor(Integer parallelism, String dataDir) {
        File data = new File(dataDir);
        List<File> dataFiles = Arrays.asList(data.listFiles());
        int total = dataFiles.size();
        int average = total / parallelism;
        List<List<File>> lists = splitList(dataFiles, average);
        for (int i = 0; i < lists.size(); i++) {
            int index = i % map.size();
            List<File> averageDataFiles = lists.get(i);
            DorisStreamLoader dorisStreamLoader = map.get(index);
            executor.execute(() -> streamLoad(dorisStreamLoader, averageDataFiles));
        }
    }

    private static void streamLoad(DorisStreamLoader dorisStreamLoader, List<File> files) {
        for (int i = 0; i < files.size(); i++) {
            File dataFile = files.get(i);
            String data = getData(dataFile);
            dorisStreamLoader.loadBatch(data);
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
