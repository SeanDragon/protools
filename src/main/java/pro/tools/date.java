package pro.tools;

import org.apache.log4j.Logger;
import pro.mojo.redis.time.TimeClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类，包括格式化、时间差值
 * Created by Administrator on 2016/3/7.
 * @author Steven Duan
 * @version 1.0
 */
public final class date {
    private final static Logger log = Logger.getLogger(date.class);

    /**
     * 获得当前时间
     * @return 返回当前时间
     */
    public static String getNowTime() {
        return getNowTime("yyyy-MM-dd HH:mm:ss.S");
    }

    /**
     * ==============Other Base===================
     */

    public static String getNowTime(String format) {
        return toFormat(format, TimeClient.timeClient.getDate());
    }

    public static String toFormat(Date date){
        return toFormat(format.hms,date);
    }

    public static String toFormat(String format, Date date){
        return new SimpleDateFormat(format).format(date);
    }

    public static String toISODate(Date date){return toFormat(format.iso,date);}

    public static Date string2Date(String strDate, String pattern) {
        if (strDate == null || strDate.equals("")) {
            throw new RuntimeException("str date null");
        }
        if (pattern == null || pattern.equals("")) {
            pattern = format.ymd;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;

        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 把字符串转换成日期
     * @param dateStr 要转换的字符串
     * @param format 转换的格式
     * @return 返回转换后的日期
     */
    public static Date parseDate(String dateStr, String format){
        DateFormat df = new SimpleDateFormat(format);
        Date dt = null;
        try {
            dt = df.parse(dateStr);
        } catch (ParseException e) {
            log.error(pro.tools.tools.toException(e));
        }
        return dt;
    }

    /**
     * 把字符串转换成日期
     * @param dateStr 要转换的字符串
     * @return 返回转换后的日期
     */
    public static Date parseDate(String dateStr){
        return parseDate(dateStr, format.ymd);
    }

    public static int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得两个时间差的年数
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return year
     */
    public static int yearOf(Date fromDate, Date toDate){
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime(fromDate);
        to.setTime(toDate);
        return Math.abs(to.get(Calendar.YEAR) - from.get(Calendar.YEAR));
    }

    /**
     * 获得两个时间差的月数
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return month
     */
    public static int monthOf(Date fromDate, Date toDate){
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime(fromDate);
        to.setTime(toDate);
        int year = to.get(Calendar.YEAR) - from.get(Calendar.YEAR);
        return Math.abs(year * 12 + to.get(Calendar.MONTH) - from.get(Calendar.MONTH));
    }

    /**
     * 获得两个时间差的日数
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return day
     */
    public static int dayOf(Date fromDate, Date toDate){
        long from = fromDate.getTime();
        long to = toDate.getTime();
        long v = to - from;
        return Math.abs((int)(v / 1000 / 3600 / 24));
    }

    /**
     * 获得两个时间差的小时数
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return hour
     */
    public static int hourOf(Date fromDate, Date toDate){
        long from = fromDate.getTime();
        long to = toDate.getTime();
        long v = to - from;
        return Math.abs((int)(v / 1000 / 60 / 60));
    }

    /**
     * 获得两个时间差的分钟数
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return minute
     */
    public static int minuteOf(Date fromDate, Date toDate){
        long from = fromDate.getTime();
        long to = toDate.getTime();
        long v = to - from;
        return Math.abs((int)(v / 1000 / 60));
    }

    /**
     * 获得两个时间差的秒数
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return second
     */
    public static long secondOf(Date fromDate, Date toDate){
        long from = fromDate.getTime();
        long to = toDate.getTime();
        long v = to - from;
        return Math.abs(v / 1000);
    }

    /**
     * 把一个日期增加指定分钟
     * @param date 日期
     * @param minute 分钟数
     * @return Date
     */
    public static Date addMinute(Date date, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE,(calendar.get(Calendar.MINUTE) + minute));
        return calendar.getTime();
    }

    /**
     * 把一个日期增加指定小时
     * @param date 日期
     * @param hour 小时数
     * @return Date
     */
    public static Date addHour(Date date, int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,(calendar.get(Calendar.HOUR_OF_DAY) + hour));
        return calendar.getTime();
    }

    /**
     * 把一个日期增加指定天数
     * @param date 日期
     * @param day 增加的天数
     * @return Date
     */
    public static Date addDay(Date date, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR,(calendar.get(Calendar.DAY_OF_YEAR) + day));
        return calendar.getTime();
    }

    /**
     * 把一个日期增加指定月数
     * @param date 日期
     * @param month 增加的月数
     * @return Date
     */
    public static Date addMonth(Date date, int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH,(calendar.get(Calendar.MONTH) + month));
        return calendar.getTime();
    }

    /**
     * 按年月日生成一个日期
     * @param year 年
     * @param month 月
     * @param day 日
     * @return Date
     */
    public static Date newDate(int year, int month, int day){
        return newDate(year,month,day,0,0,0);
    }

    /**
     * 按年月日时分秒生成一个日期
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 小时
     * @param minute 分钟
     * @param second 秒
     * @return Date
     */
    public static Date newDate(int year, int month, int day, int hour, int minute, int second){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取当前最大日期
     * year 一年里最大日期
     * year month 一个月里最大日期
     * year month day 一天里最大时间
     * @return 返回最大日期
     */
    public static Date maxDate(){
        Date date = TimeClient.timeClient.getDate();
        return maxDate(getYear(date),getMonth(date),getDay(date));
    }

    public static Date maxDate(int year){
        return maxDate(year, 12);
    }

    public static Date maxDate(int year, int month){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,0);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static Date maxDate(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取当前最小日期
     * year 一年里最小日期
     * year month 一个月里最小日期
     * year month day 一天里最小时间
     * @return 返回最小日期
     */
    public static Date minDate(){
        Date date = TimeClient.timeClient.getDate();
        return minDate(getYear(date),getMonth(date),getDay(date));
    }

    public static Date minDate(int year){
        return minDate(year, 1);
    }

    public static Date minDate(int year, int month){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date minDate(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public final class format {
        /**
         * 日期格式为 yyyy-MM-dd
         */
        public static final String ymd = "yyyy-MM-dd";
        /**
         * 日期格式为 yyyy-MM-dd HH:mm:ss.SSS
         */
        public static final String hms = "yyyy-MM-dd HH:mm:ss.SSS";
        /**
         * 日期格式为 yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
         */
        public static final String iso = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    }
}
