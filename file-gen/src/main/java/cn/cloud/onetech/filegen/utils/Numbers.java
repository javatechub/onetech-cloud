package cn.cloud.onetech.filegen.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author heguanhui
 * @desc
 * @date 2024/4/12 17:55
 */
public class Numbers {

    static final Random random = new Random();

    public static Double randomDouble(int start, int end) {
        return start + (end - start) * random.nextDouble();
    }

    public static Float randomFloat(int start, int end) {
        return start + (end - start) * random.nextFloat();
    }
    public static Float randomFloat(int start, int end, int scale) {
        Float rf = start + (end - start) * random.nextFloat();
        return new BigDecimal(rf).setScale(scale, BigDecimal.ROUND_FLOOR).floatValue();
    }

    public static Integer randomInteger(int start, int end) {
        return random.nextInt(end - start + 1) + start;
    }

    public static BigDecimal randomDecimal(int start, int end) {
        return randomDecimal(start, end, 2);
    }

    public static BigDecimal randomDecimal(int start, int end, int scale) {
        Double randomDouble = randomDouble(start, end);
        return new BigDecimal(randomDouble).setScale(scale, BigDecimal.ROUND_FLOOR);
    }

    public static void main(String[] args) {
        System.out.println(randomDecimal(80, 160));
        System.out.println(randomDecimal(80, 160));
        System.out.println(randomDecimal(80, 160));
        System.out.println(randomDecimal(80, 160));
        System.out.println(randomDecimal(80, 160));
        System.out.println(randomDecimal(80, 160,3));
        System.out.println(randomDecimal(80, 160,3));
        System.out.println(randomDecimal(80, 160,3));
        System.out.println(randomDecimal(80, 160,3));
        System.out.println(randomDecimal(80, 160,3));
        System.out.println(randomDecimal(80, 160,1));
        System.out.println(randomDecimal(80, 160,1));
        System.out.println(randomDecimal(80, 160,1));
        System.out.println(randomDecimal(80, 160,1));
        System.out.println(randomDecimal(80, 160,1));
    }

}
