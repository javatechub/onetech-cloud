package cn.cloud.onetech.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/23 10:50
 */
public class PhoneNumberGenerator {

    // 定义各大运营商的前缀
    private static final String[] CHINA_MOBILE_PREFIXES = {"134", "135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "1703", "1705", "172", "178", "182", "183", "184", "187", "188", "198"};
    private static final String[] CHINA_UNICOM_PREFIXES = {"130", "131", "132", "145", "155", "156", "166", "171", "175", "176", "185", "186"};
    private static final String[] CHINA_TELECOM_PREFIXES = {"133", "149", "153", "173", "177", "180", "181", "189", "191", "199"};

    // 随机生成手机号后8位
    private static String generateRandomSuffix() {
        Random random = new Random();
        StringBuilder suffix = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            suffix.append(random.nextInt(10));
        }
        return suffix.toString();
    }

    // 随机选择一个运营商并生成手机号
    public static String generateRandomPhoneNumber() {
        Random random = new Random();
        String prefix;
        int operatorIndex = random.nextInt(3); // 0: China Mobile, 1: China Unicom, 2: China Telecom

        switch (operatorIndex) {
            case 0:
                prefix = CHINA_MOBILE_PREFIXES[random.nextInt(CHINA_MOBILE_PREFIXES.length)];
                break;
            case 1:
                prefix = CHINA_UNICOM_PREFIXES[random.nextInt(CHINA_UNICOM_PREFIXES.length)];
                break;
            case 2:
                prefix = CHINA_TELECOM_PREFIXES[random.nextInt(CHINA_TELECOM_PREFIXES.length)];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operatorIndex);
        }
        return prefix + generateRandomSuffix();
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 500_000; i++) {
            list.add(generateRandomPhoneNumber());
        }
        System.out.println(list.stream().distinct().count());
    }

}
