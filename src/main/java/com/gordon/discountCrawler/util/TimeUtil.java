package com.gordon.discountCrawler.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wwz on 2016/2/22.
 */
public class TimeUtil {
    private static final Logger log = Logger.getLogger(TimeUtil.class.getName());

    /**
     * 将格式化时间转换为Date对象
     * @param time
     * @return
     */
    public static Date parseTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
