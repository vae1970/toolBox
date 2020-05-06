package com.vanilla.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @description: date util
 * @author: vae1970
 * @create: 2020-05-06 23:07
 **/
public class DateUtil {

    /**
     * get timestamp
     *
     * @return timestamp
     */
    public static Long getTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now()).getTime();
    }

}
