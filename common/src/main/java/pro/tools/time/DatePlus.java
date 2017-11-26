package pro.tools.time;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * Created on 17/4/8 19:27 星期六. 替代时间类的类
 *
 * @author SeanDragon
 */
public class DatePlus implements Serializable, Cloneable {
    /**
     * 日期对象
     */
    private LocalDateTime localDateTime;

    //region 初始化区域
    public DatePlus() {
        this.localDateTime = LocalDateTime.now();
    }

    public DatePlus(LocalDateTime localDateTime) {
        this.localDateTime = LocalDateTime.of(localDateTime.toLocalDate(), localDateTime.toLocalTime());
    }

    public DatePlus(int year, int month, int dayOfMonth) {
        this.localDateTime = LocalDateTime.of(LocalDate.of(year, month, dayOfMonth), LocalTime.MIN);
    }

    public DatePlus(int hour, int minutes) {
        this.localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minutes));
    }

    public DatePlus(int year, int month, int dayOfMonth, int hour, int minutes) {
        this.localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minutes);
    }

    public DatePlus(int year, int month, int dayOfMonth, int hour, int minutes, int seconds) {
        this.localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minutes, seconds);
    }

    public DatePlus(int year, int month, int dayOfMonth, int hour, int minutes, int seconds, int nanoOfSecond) {
        this.localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minutes, seconds, nanoOfSecond);
    }
    //endregion

    public DatePlus(long time) {
        this.localDateTime = ToolDatePlus.time2LocalDateTime(time);
    }

    public DatePlus(java.util.Date date) {
        this.localDateTime = ToolDatePlus.date2LocalDateTime(date);
    }

    public DatePlus(String dateStr, String pattern) {
        try {
            this.localDateTime = LocalDateTime.parse(dateStr, ToolDatePlus.pattern(pattern));
        } catch (DateTimeParseException e) {
            LocalDate date = LocalDate.parse(dateStr, ToolDatePlus.pattern(pattern));
            LocalTime time = LocalTime.MIN;
            this.localDateTime = LocalDateTime.of(date, time);
        }
    }

    public DatePlus(String dateStr, DateTimeFormatter dateTimeFormatter) {
        try {
            this.localDateTime = LocalDateTime.parse(dateStr, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            LocalDate date = LocalDate.parse(dateStr, dateTimeFormatter);
            LocalTime time = LocalTime.MIN;
            this.localDateTime = LocalDateTime.of(date, time);
        }
    }

    //region 添加属性值
    public DatePlus addYear(long year) {
        this.localDateTime = this.localDateTime.plusYears(year);
        return this;
    }

    public DatePlus addMonth(long month) {
        this.localDateTime = this.localDateTime.plusMonths(month);
        return this;
    }

    public DatePlus addWeek(long week) {
        this.localDateTime = this.localDateTime.plusWeeks(week);
        return this;
    }

    public DatePlus addDay(long dayOfMonth) {
        this.localDateTime = this.localDateTime.plusDays(dayOfMonth);
        return this;
    }

    public DatePlus addHour(long hour) {
        this.localDateTime = this.localDateTime.plusHours(hour);
        return this;
    }

    public DatePlus addMinutes(long minutes) {
        this.localDateTime = this.localDateTime.plusMinutes(minutes);
        return this;
    }

    public DatePlus addSeconds(long seconds) {
        this.localDateTime = this.localDateTime.plusSeconds(seconds);
        return this;
    }

    public DatePlus addNano(long nanoSeconds) {
        this.localDateTime = this.localDateTime.plusNanos(nanoSeconds);
        return this;
    }
    //endregion


    //region 获取属性值

    /**
     * 获取年份
     *
     * @return 年
     */
    public int getYear() {
        return this.localDateTime.getYear();
    }

    /**
     * 获取月份
     *
     * @return 月
     */
    public int getMonth() {
        return this.localDateTime.getMonthValue();
    }

    /**
     * 获取月份对象
     *
     * @return 对象
     */
    public Month getMonthObj() {
        return this.localDateTime.getMonth();
    }

    /**
     * 获取天在这周的次序
     *
     * @return 天
     */
    public int getDayOfWeek() {
        return this.localDateTime.getDayOfWeek().getValue();
    }

    /**
     * 获取天在这周的对象
     *
     * @return 对象
     */
    public DayOfWeek getDayOfWeekObj() {
        return this.localDateTime.getDayOfWeek();
    }

    /**
     * 获取天在这月的次序
     *
     * @return 天
     */
    public int getDayOfMonth() {
        return this.localDateTime.getDayOfMonth();
    }

    /**
     * 获取天在这年的次序
     *
     * @return 天
     */
    public int getDayOfYear() {
        return this.localDateTime.getDayOfYear();
    }

    /**
     * 获取小时数
     *
     * @return
     */
    public int getHour() {
        return this.localDateTime.getHour();
    }

    /**
     * 获取分钟数
     *
     * @return
     */
    public int getMinute() {
        return this.localDateTime.getMinute();
    }

    /**
     * 获取秒数
     *
     * @return
     */
    public int getSecond() {
        return this.localDateTime.getSecond();
    }
    //endregion

    /**
     * 获取纳秒数
     *
     * @return
     */
    public int getNanoSecond() {
        return this.localDateTime.getNano();
    }

    /**
     * 获取这个月的最后一天
     *
     * @return 天
     */
    public DatePlus getLastDayOfMonth() {
        return new DatePlus(this.localDateTime.with(TemporalAdjusters.lastDayOfMonth()));
    }

    /**
     * 获取这一年的最后一天
     *
     * @return 天
     */
    public DatePlus getLastDayOfYear() {
        return new DatePlus(this.localDateTime.with(TemporalAdjusters.lastDayOfYear()));
    }

    /**
     * 获取这个月的最后一天
     *
     * @return 天
     */
    public DatePlus getFirstDayOfMonth() {
        return new DatePlus(this.localDateTime.with(TemporalAdjusters.firstDayOfMonth()));
    }

    /**
     * 获取这一年的第一天
     *
     * @return 天
     */
    public DatePlus getFirstDayOfYear() {
        return new DatePlus(this.localDateTime.with(TemporalAdjusters.firstDayOfYear()));
    }

    /**
     * 获取这个月的最后一天
     *
     * @return 天
     */
    public DatePlus getFirstDayOfNextMonth() {
        return new DatePlus(this.localDateTime.with(TemporalAdjusters.firstDayOfNextMonth()));
    }

    /**
     * 获取这一年的第一天
     *
     * @return 天
     */
    public DatePlus getFirstDayOfNextYear() {
        return new DatePlus(this.localDateTime.with(TemporalAdjusters.firstDayOfNextYear()));
    }

    /**
     * 是否是闰年
     *
     * @return result
     */
    public boolean isLeapYear() {
        return this.localDateTime.toLocalDate().isLeapYear();
    }

    //region 属性的比较
    public boolean isBefore(DatePlus datePlus, DateType dateType) {
        boolean isBefore;
        switch (dateType) {
            case YEAR:
                isBefore = ofYear(datePlus) < 0;
                break;
            case MONTH:
                isBefore = ofMonth(datePlus) < 0;
                break;
            case DAY:
                isBefore = ofDay(datePlus) < 0;
                break;
            case HOUR:
                isBefore = ofHour(datePlus) < 0;
                break;
            case MINUTES:
                isBefore = ofMinutes(datePlus) < 0;
                break;
            case SECONDS:
                isBefore = ofSeconds(datePlus) < 0;
                break;
            default:
                isBefore = ofSeconds(datePlus) < 0;
                break;
        }
        return isBefore;
        //return this.localDateTime.isBefore(datePlus.getLocalDateTime());
    }

    public boolean isSame(DatePlus datePlus, DateType dateType) {
        boolean isSame;
        switch (dateType) {
            case YEAR:
                isSame = ofYear(datePlus) == 0;
                break;
            case MONTH:
                isSame = ofMonth(datePlus) == 0;
                break;
            case DAY:
                isSame = ofDay(datePlus) == 0;
                break;
            case HOUR:
                isSame = ofHour(datePlus) == 0;
                break;
            case MINUTES:
                isSame = ofMinutes(datePlus) == 0;
                break;
            case SECONDS:
                isSame = ofSeconds(datePlus) == 0;
                break;
            default:
                isSame = ofSeconds(datePlus) == 0;
                break;
        }
        return isSame;
        //return this.localDateTime.isEqual(datePlus.getLocalDateTime());
    }

    public boolean isAfter(DatePlus datePlus, DateType dateType) {
        boolean isAfter;
        switch (dateType) {
            case YEAR:
                isAfter = ofYear(datePlus) > 0;
                break;
            case MONTH:
                isAfter = ofMonth(datePlus) > 0;
                break;
            case DAY:
                isAfter = ofDay(datePlus) > 0;
                break;
            case HOUR:
                isAfter = ofHour(datePlus) > 0;
                break;
            case MINUTES:
                isAfter = ofMinutes(datePlus) > 0;
                break;
            case SECONDS:
                isAfter = ofSeconds(datePlus) > 0;
                break;
            default:
                isAfter = ofSeconds(datePlus) > 0;
                break;
        }
        return isAfter;
        //return this.localDateTime.isAfter(datePlus.getLocalDateTime());
    }

    public long ofYear(DatePlus datePlus) {
        return ofDateTime(ChronoUnit.YEARS, datePlus);
    }

    public long ofMonth(DatePlus datePlus) {
        return ofDateTime(ChronoUnit.MONTHS, datePlus);
    }

    public long ofDay(DatePlus datePlus) {
        return ofDateTime(ChronoUnit.DAYS, datePlus);
    }

    public long ofHour(DatePlus datePlus) {
        return ofDateTime(ChronoUnit.HOURS, datePlus);
    }

    public long ofMinutes(DatePlus datePlus) {
        return ofDateTime(ChronoUnit.MINUTES, datePlus);
    }

    public long ofSeconds(DatePlus datePlus) {
        return ofDateTime(ChronoUnit.SECONDS, datePlus);
    }

    public long ofNanos(DatePlus datePlus) {
        return ofDateTime(ChronoUnit.NANOS, datePlus);
    }

    public long ofDateTime(ChronoUnit chronoUnit, DatePlus datePlus) {
        return -chronoUnit.between(this.localDateTime, datePlus.localDateTime);
    }
    //endregion


    //region 输出部分

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    public DatePlus toMinDate(DateType dateType) {
        switch (dateType) {
            case YEAR:
                this.localDateTime = LocalDateTime.of(getYear(), 1, 1, 0, 0, 0, 0);
                break;
            case MONTH:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), 1, 0, 0, 0, 0);
                break;
            case DAY:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), 0, 0, 0, 0);
                break;
            case HOUR:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), getHour(), 0, 0, 0);
                break;
            case MINUTES:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), getHour(), getMinute(), 0, 0);
                break;
            case SECONDS:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), getHour(), getMinute(), getSecond(), 0);
                break;
            default:
                break;
        }
        return this;
    }

    public DatePlus toMaxDate(DateType dateType) {
        switch (dateType) {
            case YEAR:
                this.localDateTime = LocalDateTime.of(getYear(), 12, 31, 23, 59, 59, 999_999_999);
                break;
            case MONTH:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getLastDayOfMonth().getDayOfMonth(), 23, 59, 59, 999_999_999);
                break;
            case DAY:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), 23, 59, 59, 999_999_999);
                break;
            case HOUR:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), getHour(), 59, 59, 999_999_999);
                break;
            case MINUTES:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), getHour(), getMinute(), 59, 999_999_999);
                break;
            case SECONDS:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), getHour(), getMinute(), getSecond(), 999_999_999);
                break;
            default:
                break;
        }
        return this;
    }

    public java.util.Date toDate() {
        return ToolDatePlus.localDateTime2Date(this.localDateTime);
    }

    /**
     * 天干地支 可换成枚举
     *
     * @return 天干地支名称
     */
    public String toGanZhi() {
        return ToolLunar.lunarYearToGanZhi(toLunar().getLunarYear());
    }

    /**
     * 获取星座
     *
     * @return 星座
     */
    public String toZodiac() {
        int month = getMonth();
        int day = getDayOfMonth();
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

    /**
     * 获取生肖
     *
     * @return 生肖
     */
    public String toChineseZodiac() {
        return CHINESE_ZODIAC[getYear() % 12];
    }

    /**
     * 获取阴历对象
     *
     * @return 阴历对象
     */
    public Lunar toLunar() {
        Solar solar = new Solar(this.getYear(), this.getMonth(), this.getDayOfMonth());
        return ToolLunar.solartolunar(solar);
    }

    /**
     * 获取公历对象
     *
     * @return 公历对象
     */
    public Solar toSolar() {
        Lunar lunar = new Lunar(this.getYear(), this.getMonth(), this.getDayOfMonth());
        return ToolLunar.lunartosolar(lunar);
    }

    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = ToolDatePlus.pattern("yyyy-MM-dd HH:mm:ss:SSS");

    public String toString(DateTimeFormatter formatter) {
        return this.localDateTime.format(formatter);
    }

    public String toString(String pattern) {
        return this.localDateTime.format(ToolDatePlus.pattern(pattern));
    }

    @Override
    public String toString() {
        return this.toString(DEFAULT_DATE_TIME_FORMATTER);
    }
    //endregion


    //region 生肖和星座
    private static final String[] CHINESE_ZODIAC = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final String[] ZODIAC = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    //endregion

    @Override
    public boolean equals(Object object) {
        return this.localDateTime.equals(object);
    }

    @Override
    public int hashCode() {
        return this.localDateTime.hashCode();
    }

    @Override
    public DatePlus clone() {
        return new DatePlus(this.localDateTime);
    }
}
