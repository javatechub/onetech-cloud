package cn.cloud.onetech.filegen;

import cn.cloud.onetech.filegen.utils.LocalDateTimes;
import cn.cloud.onetech.filegen.utils.LocalDates;
import cn.cloud.onetech.filegen.utils.Numbers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author heguanhui
 * @desc cd doris-helper && bash ./bin/file_gen.sh 10 100 100000
 * @date 2024/10/23 16:52
 */
public class FileGen {

    private static ThreadPoolExecutor executor = null;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Integer parallelism = Integer.parseInt(System.getProperty("parallelism","10"));
        Integer rowsPerFile = Integer.parseInt(System.getProperty("rowsPerFile","50"));
        Integer totalCount = Integer.parseInt(System.getProperty("totalCount","100"));
        initExecutor(parallelism, totalCount);
        runExecutor(totalCount, rowsPerFile);
        shutdownExecutor();
    }

    // 方法：获取小于或等于n的最大2的幂
    public static int floorPowerOfTwo(int n) {
        if (n != 0 && n % 2 == 0) {
            return n;
        }
        int power = 1;
        while (power <= n) {
            power <<= 1; // 左移一位，相当于乘以2
        }
        return power; // 右移一位，得到小于或等于n的最大2的幂
    }

    // 方法：获取大于或等于n的最小2的幂
    public static int ceilPowerOfTwo(int n) {
        int power = 1;
        while (power < n) {
            power <<= 1; // 左移一位，相当于乘以2
        }
        return power;
    }

    // 方法：获取离n最近的2的幂
    public static int nearestPowerOfTwo(int n) {
        int floor = floorPowerOfTwo(n);
        int ceil = ceilPowerOfTwo(n);

        // 计算两个2的幂与n的距离
        int diffFloor = n - floor;
        int diffCeil = ceil - n;

        // 返回距离最小的那个2的幂
        return diffFloor <= diffCeil ? floor : ceil;
    }

    private static void initExecutor(Integer parallelism, Integer totalCount) {
        executor = new ThreadPoolExecutor(
                parallelism,
                parallelism * 4,
                30000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(nearestPowerOfTwo(totalCount))
        );
    }

    private static void runExecutor(Integer totalCount, Integer rowsPerFile) {
        String fileOutputDir = System.getProperty("fileOutputDir", "./data");
        for (int i = 1; i <= totalCount; i++) {
            final int index = i;
            executor.execute(() -> generateCSVFile(index, rowsPerFile, fileOutputDir));
        }
    }

    private static void shutdownExecutor() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    private static void generateCSVFile(int fileIndex, Integer rowsPerFile, String fileOutputDir) {
        Path filePath = Paths.get(fileOutputDir + "/" + "data_" + fileIndex + ".csv");
        long startTimestamp = System.currentTimeMillis();
        try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            // 写入数据行
            long startId = (fileIndex - 1) * rowsPerFile;
            String uuid = UUID.randomUUID().toString();
            for (int i = 0; i < rowsPerFile; i++) {
                List<String> line = new ArrayList<>();
                startId++;
                String col1 = String.valueOf(startId);
                String col2 = uuid;
                String col3 = LocalDates.random().toString();
                String col4 = LocalDateTimes.random().format(FORMATTER);
                String col5 = Numbers.randomDecimal(1, 100000, 10).toString();
                String col6 = String.valueOf(Numbers.randomDouble(1, 1000000));
                String col7 = String.valueOf(Numbers.randomFloat(1, 1000000, 8));
                String col8 = Numbers.randomInteger(1,2147483647).toString();
                String col9 = Numbers.randomInteger(1,10000000).toString();
                String col10 = Numbers.randomInteger(1,32767).toString();
                String col11 = Numbers.randomInteger(1,127).toString();
                long currentTimeMillis = System.currentTimeMillis();
                String col12 = uuid + "-" + currentTimeMillis + "_" + 1;
                String col13 = uuid + "-" + currentTimeMillis + "_" + 2;
                String col14 = uuid + "-" + currentTimeMillis + "_" + 3;
                String col15 = uuid + "-" + currentTimeMillis + "_" + 4;
                String col16 = uuid + "-" + currentTimeMillis + "_" + 5;
                String col17 = uuid + "-" + currentTimeMillis + "_" + 6;
                String col18 = uuid + "-" + currentTimeMillis + "_" + 7;
                String col19 = uuid + "-" + currentTimeMillis + "_" + 8;
                String col20 = uuid + "-" + currentTimeMillis + "_" + 9;
                String col21 = uuid + "-" + currentTimeMillis + "_" + 10;
                String col22 = uuid + "-" + currentTimeMillis + "_" + 11;
                String col23 = uuid + "-" + currentTimeMillis + "_" + 12;
                String col24 = uuid + "-" + currentTimeMillis + "_" + 13;
                String col25 = uuid + "-" + currentTimeMillis + "_" + 14;
                String col26 = uuid + "-" + currentTimeMillis + "_" + 15;
                String col27 = uuid + "-" + currentTimeMillis + "_" + 16;
                String col28 = uuid + "-" + currentTimeMillis + "_" + 17;
                String col29 = uuid + "-" + currentTimeMillis + "_" + 18;
                String col30 = uuid + "-" + currentTimeMillis + "_" + 19;
                String col31 = uuid + "-" + currentTimeMillis + "_" + 20;
                String col32 = uuid + "-" + currentTimeMillis + "_" + 21;
                String col33 = uuid + "-" + currentTimeMillis + "_" + 22;
                String col34 = uuid + "-" + currentTimeMillis + "_" + 23;
                String col35 = uuid + "-" + currentTimeMillis + "_" + 24;
                String col36 = uuid + "-" + currentTimeMillis + "_" + 25;
                String col37 = uuid + "-" + currentTimeMillis + "_" + 26;
                String col38 = uuid + "-" + currentTimeMillis + "_" + 27;
                String col39 = uuid + "-" + currentTimeMillis + "_" + 28;
                String col40 = uuid + "-" + currentTimeMillis + "_" + 29;
                String col41 = uuid + "-" + currentTimeMillis + "_" + 30;
                String col42 = uuid + "-" + currentTimeMillis + "_" + 31;
                String col43 = uuid + "-" + currentTimeMillis + "_" + 32;
                String col44 = uuid + "-" + currentTimeMillis + "_" + 33;
                String col45 = uuid + "-" + currentTimeMillis + "_" + 34;
                String col46 = uuid + "-" + currentTimeMillis + "_" + 35;
                String col47 = uuid + "-" + currentTimeMillis + "_" + 36;
                String col48 = uuid + "-" + currentTimeMillis + "_" + 37;
                String col49 = uuid + "-" + currentTimeMillis + "_" + 38;
                String col50 = uuid + "-" + currentTimeMillis + "_" + 39;
                line.add(col1);
                line.add(col2);
                line.add(col3);
                line.add(col4);
                line.add(col5);
                line.add(col6);
                line.add(col7);
                line.add(col8);
                line.add(col9);
                line.add(col10);
                line.add(col11);
                line.add(col12);
                line.add(col13);
                line.add(col14);
                line.add(col15);
                line.add(col16);
                line.add(col17);
                line.add(col18);
                line.add(col19);
                line.add(col20);
                line.add(col21);
                line.add(col22);
                line.add(col23);
                line.add(col24);
                line.add(col25);
                line.add(col26);
                line.add(col27);
                line.add(col28);
                line.add(col29);
                line.add(col30);
                line.add(col31);
                line.add(col32);
                line.add(col33);
                line.add(col34);
                line.add(col35);
                line.add(col36);
                line.add(col37);
                line.add(col38);
                line.add(col39);
                line.add(col40);
                line.add(col41);
                line.add(col42);
                line.add(col43);
                line.add(col44);
                line.add(col45);
                line.add(col46);
                line.add(col47);
                line.add(col48);
                line.add(col49);
                line.add(col50);
                final boolean isLastLine = (i == rowsPerFile - 1);
                String lineContent = String.join(",", line) + (isLastLine ? "" : "\n");
                System.out.printf("current line content bytes: %s%n", lineContent.getBytes(StandardCharsets.UTF_8).length);
                ByteBuffer buffer = ByteBuffer.wrap(lineContent.getBytes());
                long position = fileChannel.size(); // 获取当前文件大小，以便追加写入（虽然这里每次都是从0开始写，但保持这个习惯以防未来修改）
                fileChannel.write(buffer, position);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTimestamp = System.currentTimeMillis();
        System.out.printf("generate file %s cost time %s, rows in file is %s%n", filePath.toFile().getAbsolutePath(), (endTimestamp - startTimestamp), rowsPerFile);
    }
}
