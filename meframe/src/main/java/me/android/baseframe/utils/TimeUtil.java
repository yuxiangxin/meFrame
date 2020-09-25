package me.android.baseframe.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import me.android.baseframe.R;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {
    public static final String FORMAT_YMD = "yyyy-MM-dd";
    public static final String FORMAT_DETAIL = "yyyy-MM-dd:HH:mm:ss";

    public static Long formatDate (String formatDate, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        Date dt;
        try {
            dt = sdf.parse(date);
            return dt.getTime();
        } catch (ParseException e) {
            LogUtils.e("TimeUtil", "" + e.getMessage());
        }
        return -1L;
    }


    public static boolean inCurrentWeek (Long mills) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周开始日期
        long firstDay = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_WEEK, 7);
        // 所在周结束日期
        long endDay = cal.getTimeInMillis();
        return firstDay <= mills && endDay > mills;
    }

    public static String getSystemDataTime () {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        sdf.format(date);
        return sdf.format(date);
    }

    public static Date getNextYearEndDataTime () {
        return getNextYearEndDataTime(System.currentTimeMillis());
    }

    public static Date getNextYearEndDataTime (long currentTimeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(currentTimeMillis));
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String formatTime (String pattern) {
        return formatTime(pattern, new Date());
    }

    public static String formatTime (String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String formatTime (String pattern, String mill) {
        return formatTime(pattern, Long.parseLong(mill));
    }

    public static String formatTime (String pattern, long mill) {
        try {
            Date date = new Date(mill);
            return formatTime(pattern, date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static long getMillis (int days) {
        return 24 * 60 * 60 * 1000 * days;
    }


    public static long getFirstDayOfYear () {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar e = Calendar.getInstance();
            e.setTimeInMillis(System.currentTimeMillis());
            int year = e.get(1);
            e.clear();
            e.set(1, year);
            return e.getTimeInMillis();
        } catch (Exception var4) {
            return 0L;
        }
    }

    public static long getYesterdayTime () {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar e = Calendar.getInstance();
            e.add(5, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today_time = sdf.format(e.getTime());
            return sdf.parse(today_time).getTime();
        } catch (ParseException var3) {
            var3.printStackTrace();
            return 0L;
        }
    }

    public static String formatDateString (long time, String pattern) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(Long.valueOf(time));
    }

    /**
     * @return true if clock is set to 24-hour mode
     */
    public static boolean is24HourFormat (final Context context) {
        return android.text.format.DateFormat.is24HourFormat(context);
    }

    public static String getDayAtWeek (long mills,String[] weekDays) {
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = new Date(mills);
        cal.setTime(datet);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }



    public static String formatTime2 (String pattern, long milli) {
        int m = (int) (milli / DateUtils.MINUTE_IN_MILLIS);
        int s = (int) ((milli / DateUtils.SECOND_IN_MILLIS) % 60);
        String mm = String.format(Locale.getDefault(), "%02d", m);
        String ss = String.format(Locale.getDefault(), "%02d", s);
        return pattern.replace("mm", mm).replace("ss", ss);
    }


    public static boolean isToday (long mills) {
        long currentMills = System.currentTimeMillis();
        if (Math.abs(currentMills - mills) > 24 * 60 * 60 * 1000) {
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(currentMills));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // 今天00:00
        long firstDay = cal.getTimeInMillis();
        cal.add(Calendar.HOUR, 24);
        // 今天24:00
        long endDay = cal.getTimeInMillis();
        return firstDay <= mills && endDay > mills;
    }

}