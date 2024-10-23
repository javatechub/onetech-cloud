package cn.cloud.onetech.filegen.utils;

import java.time.LocalDateTime;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/23 19:47
 */
public class LocalDateTimes {

    public static LocalDateTime random() {
        Integer offset = Numbers.randomInteger(1, 100000000);
        return LocalDateTime.now().minusSeconds(offset);
    }

}
