package cn.cloud.onetech.sl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/24 15:14
 */
public class ShellExecutor {

    public static String execute(String command) {
        // 要执行的shell命令
        String[] commands = {"/bin/sh", "-c", command};
        // 替换上面的"your_command_here"为你想要执行的shell命令
        // 例如，要列出当前目录的内容，可以使用 "ls -l"

        ProcessBuilder processBuilder = new ProcessBuilder(commands);

        // 可以设置工作目录、环境变量等（可选）
        // processBuilder.directory(new File("/path/to/working/directory"));
        // Map<String, String> env = processBuilder.environment();
        // env.put("VAR_NAME", "value");

        try {
            // 启动进程
            Process process = processBuilder.start();

            // 获取命令执行的输出（标准输出）
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // 获取命令执行的错误输出（标准错误）
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            // 等待进程结束并获取退出状态
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);

            // 打印输出和错误输出
            System.out.println("Output:\n" + output.toString());
            if (errorOutput.length() > 0) {
                System.err.println("Error Output:\n" + errorOutput.toString());
            }
            if (exitCode == 0) {
                return output.toString();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

}
