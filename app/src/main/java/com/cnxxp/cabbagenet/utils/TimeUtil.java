package com.cnxxp.cabbagenet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2017/4/14 0014.
 */

public class TimeUtil {
    private TimeUtil() {

    }

    public static String transationSysTime( long timeType) {
        if (timeType == 0)
            return null;
        String result = "";
        timeType *= 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result = format.format(new Date(timeType));
        transationTimetoInt(format, result);//将时间转换为Int类型，方便计算  
        return result;
    }

    public static void transationTimetoInt(SimpleDateFormat format, String resultStr) {
        try {
            Date date = format.parse(resultStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);//month从0开始  
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);
        } catch (ParseException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
    }

    public static String programTimes(String add_time) {
        long limit = System.currentTimeMillis() - Long.parseLong(add_time) * 1000;
        if (limit > 86400000) {
            //超过1天，显示具体日期 
            return add_time;
        } else if (limit > 3600000) {
            //超过1小时，显示n小时前
            return limit / 1000 / 60 / 60 + "小时前";
        } else {
            //1小时内，显示N分钟
            return limit / 1000 / 60 + "分钟前";
        }
    }
}
