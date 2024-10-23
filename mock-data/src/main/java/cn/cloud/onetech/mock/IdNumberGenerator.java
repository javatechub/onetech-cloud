package cn.cloud.onetech.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/23 10:21
 */
public class IdNumberGenerator {

    // 地区码（前6位），根据国家统计局发布的标准
    private static final String[] AREA_CODES = {
            "110000", "120000", "130000", "140000", "150000", "210000", "220000", "230000",
            "310000", "320000", "330000", "340000", "350000", "360000", "370000", "410000",
            "420000", "430000", "440000", "450000", "460000", "500000", "510000", "520000",
            "530000", "540000", "610000", "620000", "630000", "640000", "650000", "710000",
            "810000", "820000", "469000", "469001", "469002", "469003", "469005", "469006",
            "469007", "469021", "469022", "469023", "469024", "469025", "469026", "469027",
            "469028", "469030", "469031"
    };

    // 出生年份（第7-10位），假设为1950-2020年
    private static final int YEAR_START = 1950;
    private static final int YEAR_END = 2020;

    // 出生月份（第11-12位），1-12月
    private static final int MONTH_START = 1;
    private static final int MONTH_END = 12;

    // 出生日（第13-14位），1-31日（具体日期需要考虑月份和闰年）
    private static final int DAY_START = 1;
    private static final int DAY_END = 31;

    // 顺序码（第15-17位），奇数分配给男性，偶数分配给女性
    private static final int SEQUENCE_START = 1;
    private static final int SEQUENCE_END = 999;

    // 校验码（第18位），根据前17位计算得出
    private static final char[] CHECK_CODES = {
            '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'
    };

    // 权重因子
    private static final int[] WEIGHT_FACTORS = {
            7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            boolean isMale = i % 2 == 0; // 可以根据需要设置为true或false以生成男性或女性的身份证
            String idCard = generateIDCard(isMale);
            list.add(idCard);
        }
        System.out.println(list.stream().distinct().count());
    }

    public static String generateIDCard(boolean isMale) {
        Random random = new Random();

        // 生成地区码
        String areaCode = AREA_CODES[random.nextInt(AREA_CODES.length)];

        // 生成出生年份
        int year = YEAR_START + random.nextInt(YEAR_END - YEAR_START + 1);

        // 生成出生月份
        int month = MONTH_START + random.nextInt(MONTH_END - MONTH_START + 1);

        // 生成出生日期（考虑月份和闰年）
        int day = getRandomDay(year, month);

        // 生成顺序码，并确保奇偶性符合性别要求
        int sequence = SEQUENCE_START + random.nextInt((SEQUENCE_END - SEQUENCE_START + 1) / 2) * 2;
        if (!isMale) {
            sequence += 1; // 女性为偶数
        }

        // 拼接前17位
        StringBuilder idCard = new StringBuilder(areaCode);
        idCard.append(String.format("%04d", year));
        idCard.append(String.format("%02d", month));
        idCard.append(String.format("%02d", day));
        idCard.append(String.format("%03d", sequence));

        // 计算校验码
        char checkCode = calculateCheckCode(idCard.toString());

        // 拼接完整的18位身份证号
        idCard.append(checkCode);

        return idCard.toString();
    }

    private static int getRandomDay(int year, int month) {
        Random random = new Random();
        int[] daysInMonth = {31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return DAY_START + random.nextInt(daysInMonth[month - 1] - DAY_START + 1);
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private static char calculateCheckCode(String idCardBase) {
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            char ch = idCardBase.charAt(i);
            int value = Character.isDigit(ch) ? Character.getNumericValue(ch) : (ch - 'A' + 10);
            sum += value * WEIGHT_FACTORS[i];
        }
        int mod = sum % 11;
        return CHECK_CODES[mod];
    }
}
