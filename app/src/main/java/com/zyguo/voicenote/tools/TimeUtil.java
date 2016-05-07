package com.zyguo.voicenote.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zyguo on 2016/5/7.
 * To get the date, time and so on.
 */
public class TimeUtil {

    /**
     * get current year
     * @return sample: 2016年
     */
    public static String getYear() {
        long stamp = System.currentTimeMillis();
        Date date = new Date(stamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年-MM月-dd日");
        String str = format.format(date);
        return str.split("-")[0];
    }

    /**
     * get current month
     * @return sample: 5月
     */
    public static String getMonth() {
        long stamp = System.currentTimeMillis();
        Date date = new Date(stamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年-MM月-dd日");
        String str = format.format(date);
        return str.split("-")[1];
    }

    /**
     * get current day
     * @return sample: 10日
     */
    public static String getDay() {
        long stamp = System.currentTimeMillis();
        Date date = new Date(stamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年-MM月-dd日");
        String str = format.format(date);
        return str.split("-")[2];
    }

    /**
     * get current date.
     * @return sample: 2016年5月4日
     */
    public static String getDate() {
        long stamp = System.currentTimeMillis();
        Date date = new Date(stamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年-MM月-dd日");
        String str = format.format(date);
        return str;
    }

    /**
     * get current day of the week.
     * @return sample: 星期一
     */
    public static int getWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
}
