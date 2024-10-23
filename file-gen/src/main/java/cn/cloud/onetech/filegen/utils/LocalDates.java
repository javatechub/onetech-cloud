package cn.cloud.onetech.filegen.utils;

import java.time.LocalDate;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/23 19:46
 */
public class LocalDates {

    public static LocalDate random() {
        Integer offset = Numbers.randomInteger(1, 10000);
        return LocalDate.now().minusDays(offset);
    }

}
