package pro.tools.time;

import com.google.common.base.Objects;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Created on 17/4/8 19:27 星期六.
 * 替代时间类的类
 *
 * @author SeanDragon
 */
public class DatePlus {
    /**
     * 日期对象
     */
    private LocalDateTime localDateTime;

    //region 初始化区域
    public DatePlus() {
        this.localDateTime = LocalDateTime.now();
    }

    public DatePlus(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public DatePlus(int year, int month, int dayOfMonth) {
        this.localDateTime = LocalDateTime.of(LocalDate.of(year, month, dayOfMonth), LocalTime.now());
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

    public DatePlus(long time) {
        java.util.Date date = new java.util.Date(time);
        this.localDateTime = ToolDateTime.date2LocalDateTime(date);
        this.localDateTime = ToolDateTime.date2LocalDateTime(date);
    }

    public DatePlus(java.util.Date date) {
        this.localDateTime = ToolDateTime.date2LocalDateTime(date);
        this.localDateTime = ToolDateTime.date2LocalDateTime(date);
    }

    public DatePlus(String dateStr, String pattern) {
        this.localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }
    //endregion

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
    public int getLastDayOfMonth() {
        return this.localDateTime.getMonth().length(isLeapYear());
    }

    /**
     * 是否是闰年
     *
     * @return result
     */
    public boolean isLeapYear() {
        return this.localDateTime.toLocalDate().isLeapYear();
    }
    //endregion

    //region 属性的比较
    public boolean isBefore(DatePlus datePlus) {
        return this.localDateTime.isBefore(datePlus.getLocalDateTime());
    }

    public boolean isSame(DatePlus datePlus) {
        return this.localDateTime.isEqual(datePlus.getLocalDateTime());
    }

    public boolean isAfter(DatePlus datePlus) {
        return this.localDateTime.isAfter(datePlus.getLocalDateTime());
    }

    public long ofYear(DatePlus datePlus) {
        return this.betweenDate(datePlus).getYears();
    }

    public long ofMonth(DatePlus datePlus) {
        return this.betweenDate(datePlus).getMonths();
    }

    public long ofDay(DatePlus datePlus) {
        return this.betweenDate(datePlus).getDays();
    }

    public long ofHour(DatePlus datePlus) {
        return this.ofSeconds(datePlus) / 60;
    }

    public long ofMinutes(DatePlus datePlus) {
        return this.ofSeconds(datePlus) / 60;
    }

    public long ofSeconds(DatePlus datePlus) {
        return (this.ofDay(datePlus) * 24 * 60 * 60) + this.betweenTime(datePlus).getSeconds();
    }

    public Period betweenDate(DatePlus datePlus) {
        return Period.between(this.localDateTime.toLocalDate(), datePlus.getLocalDateTime().toLocalDate());
    }

    public Duration betweenTime(DatePlus datePlus) {
        return Duration.between(this.localDateTime.toLocalTime(), datePlus.getLocalDateTime().toLocalTime());
    }
    //endregion

    //region 输出部分
    @Override
    public String toString() {
        return this.toString("yyyy-MM-dd HH:mm:ss.SSS");
    }

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
        }
        return this;
    }

    public DatePlus toMaxDate(DateType dateType) {
        switch (dateType) {
            case YEAR:
                this.localDateTime = LocalDateTime.of(getYear(), 12, 31, 23, 59, 59, 999_999_999);
                break;
            case MONTH:
                this.localDateTime = LocalDateTime.of(getYear(), getMonth(), getLastDayOfMonth(), 23, 59, 59, 999_999_999);
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
        }
        return this;
    }

    public java.util.Date toDate() {
        return ToolDateTime.localDateTime2Date(this.localDateTime);
    }

    public ToolLunar.Solar toSolar() {
        ToolLunar.Lunar lunar = new ToolLunar.Lunar(this.getYear(), this.getMonth(), this.getDayOfMonth());
        return ToolLunar.LunarToSolar(lunar);
    }

    public String toGanZhi() {
        return ToolLunar.lunarYearToGanZhi(toLunar().getLunarYear());
    }

    //region 生肖和星座
    private static final String[] CHINESE_ZODIAC = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final String[] ZODIAC = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};

    public String toZodiac() {
        int month = getMonth();
        int day = getDayOfMonth();
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

    public String toChineseZodiac() {
        return CHINESE_ZODIAC[getYear() % 12];
    }
    //endregion

    public ToolLunar.Lunar toLunar() {
        ToolLunar.Solar solar = new ToolLunar.Solar(this.getYear(), this.getMonth(), this.getDayOfMonth());
        return ToolLunar.SolarToLunar(solar);
    }

    public String toString(DateTimeFormatter formatter) {
        return this.localDateTime.format(formatter);
    }

    public String toString(String pattern) {
        return this.localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    //endregion

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof DatePlus)) return false;
        DatePlus datePlus = (DatePlus) object;
        return Objects.equal(getLocalDateTime(), datePlus.getLocalDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLocalDateTime());
    }
}
