package pro.tools.time;

/**
 * 农历
 *
 * @author SeanDragon
 */
public class Lunar {
    private boolean isLeap;
    private int lunarYear;
    private int lunarMonth;
    private int lunarDay;

    public Lunar(int lunarYear, int lunarMonth, int lunarDay) {
        this.lunarDay = lunarDay;
        this.lunarMonth = lunarMonth;
        this.lunarYear = lunarYear;
    }

    public boolean getIsLeap() {
        return isLeap;
    }

    public Lunar setIsLeap(boolean leap) {
        isLeap = leap;
        return this;
    }

    public int getLunarYear() {
        return lunarYear;
    }

    public Lunar setLunarYear(int lunarYear) {
        this.lunarYear = lunarYear;
        return this;
    }

    public int getLunarMonth() {
        return lunarMonth;
    }

    public Lunar setLunarMonth(int lunarMonth) {
        this.lunarMonth = lunarMonth;
        return this;
    }

    public int getLunarDay() {
        return lunarDay;
    }

    public Lunar setLunarDay(int lunarDay) {
        this.lunarDay = lunarDay;
        return this;
    }

    @Override
    public String toString() {
        return String.format("阴历：%S年%S月%S日", lunarYear, lunarMonth, lunarDay) + (isLeap ? "（闰年）" : "");
    }
}
