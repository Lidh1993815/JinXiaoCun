package com.qianmo.jinxiaocun.fu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wizardev on 17-8-10.
 */

public class DateUtil {
    //将事件戳转化为日期
    public static String getDate(long createTime,String dateFromat){
        //"yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat format =  new SimpleDateFormat(dateFromat, Locale.CHINA);
        String date = format.format(new Date(createTime));
        return date;
    }
    //计算两个日期之间相隔的天数
    public static int daysBetween(String startDate,String endDate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(endDate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     * @param startTime 较小的时间
     * @param endDate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date startTime,Date endDate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        startTime=sdf.parse(sdf.format(startTime));
        endDate=sdf.parse(sdf.format(endDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
