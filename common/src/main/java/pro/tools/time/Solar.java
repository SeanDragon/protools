package pro.tools.time;

/**
 * 公历
 *
 * @author SeanDragon
 */
public class Solar {
    private int solarYear;
    private int solarMonth;
    private int solarDay;

    public Solar(int solarYear, int solarMonth, int solarDay) {
        this.solarYear = solarYear;
        this.solarMonth = solarMonth;
        this.solarDay = solarDay;
    }

    public int getSolarYear() {
        return solarYear;
    }

    public Solar setSolarYear(int solarYear) {
        this.solarYear = solarYear;
        return this;
    }

    public int getSolarMonth() {
        return solarMonth;
    }

    public Solar setSolarMonth(int solarMonth) {
        this.solarMonth = solarMonth;
        return this;
    }

    public int getSolarDay() {
        return solarDay;
    }

    public Solar setSolarDay(int solarDay) {
        this.solarDay = solarDay;
        return this;
    }

    @Override
    public String toString() {
        return String.format("公历：%S年%S月%S日", solarYear, solarMonth, solarDay);
    }
}