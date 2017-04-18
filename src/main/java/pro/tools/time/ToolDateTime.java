package pro.tools.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created on 17/4/8 18:55 星期六.
 *
 * @author SeanDragon
 */
public final class ToolDateTime {

    private ToolDateTime() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    //region 关于星座和生肖
    private static final String[] CHINESE_ZODIAC = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final String[] ZODIAC = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    //endregion

    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(DEFAULT_ZONE_ID);
        return zdt.toLocalDateTime();
    }

    public static LocalDate date2LocalDate(Date date) {
        return date2LocalDateTime(date).toLocalDate();
    }

    public static LocalTime date2LocalTime(Date date) {
        return date2LocalDateTime(date).toLocalTime();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(DEFAULT_ZONE_ID).toInstant();
        return Date.from(instant);
    }

    public static String format(Date date, String pattern) {
        return date2LocalDateTime(date).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取生肖
     *
     * @return 生肖
     */
    public static String getChineseZodiac(int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    /**
     * 获取星座
     *
     * @param month 月
     * @param day   日
     * @return 星座
     */
    public static String getZodiac(int month, int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

}
