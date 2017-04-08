package pro.tools.time;////
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.Serializable;
import java.util.Date;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class DatePro implements Cloneable, Serializable {
    private Date date;

    public static DatePro now() {
        return new DatePro();
    }

    public static DatePro instance(int year, int month, int day) {
        return new DatePro(year, month, day);
    }

    public static DatePro parseDate(String dateStr, String format) {
        return new DatePro(ToolDate.parseDate(dateStr, format));
    }

    public DatePro() {
        this.date = new Date();
    }

    public DatePro(Date v) {
        this.date = v;
    }

    public DatePro(int year, int month, int day) {
        this.date = ToolDate.newDate(year, month, day);
    }

    public DatePro(String dateStr) {
        String fmt = null;
        int len = dateStr.length();
        if (Math.abs(len - "yyyy-MM-dd".length()) == 0) {
            fmt = "yyyy-MM-dd";
        } else if (Math.abs(len - "yyyy-MM-dd".length()) == 0) {
            fmt = "yyyy-MM-dd HH:mm:ss.SSS";
        } else if (Math.abs(len - "yyyy-MM-dd".length()) == 0) {
            fmt = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        } else {
            this.date = new Date();
        }

        if (fmt != null) {
            try {
                this.date = ToolDate.parseDate(dateStr, fmt);
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

    }

    public int getYear() {
        return ToolDate.getYear(this.date);
    }

    public int getMonth() {
        return ToolDate.getMonth(this.date);
    }

    public int getDay() {
        return ToolDate.getDay(this.date);
    }

    public int yearOf(DatePro toDate) {
        return ToolDate.yearOf(this.date, toDate.getDate());
    }

    public int monthOf(DatePro toDate) {
        return ToolDate.monthOf(this.date, toDate.getDate());
    }

    public int dayOf(DatePro toDate) {
        return ToolDate.dayOf(this.date, toDate.getDate());
    }

    public int hourOf(DatePro toDate) {
        return ToolDate.hourOf(this.date, toDate.getDate());
    }

    public int minuteOf(DatePro toDate) {
        return ToolDate.minuteOf(this.date, toDate.getDate());
    }

    public long secondOf(DatePro toDate) {
        return ToolDate.secondOf(this.date, toDate.getDate());
    }

    public DatePro addMinute(int minute) {
        this.date = ToolDate.addMinute(this.date, minute);
        return this;
    }

    public DatePro addHour(int hour) {
        this.date = ToolDate.addHour(this.date, hour);
        return this;
    }

    public DatePro addDay(int days) {
        this.date = ToolDate.addDay(this.date, days);
        return this;
    }

    public DatePro addMonth(int month) {
        this.date = ToolDate.addMonth(this.date, month);
        return this;
    }

    public DatePro toMaxDate() {
        this.date = ToolDate.maxDate(this.getYear(), this.getMonth(), this.getDay());
        return this;
    }

    public DatePro toMaxDate(int year) {
        this.date = ToolDate.maxDate(year);
        return this;
    }

    public DatePro toMaxDate(int year, int month) {
        this.date = ToolDate.maxDate(year, month);
        return this;
    }

    public DatePro toMaxDate(int year, int month, int day) {
        this.date = ToolDate.maxDate(year, month, day);
        return this;
    }

    public DatePro toMinDate() {
        this.date = ToolDate.minDate(this.getYear(), this.getMonth(), this.getDay());
        return this;
    }

    public DatePro toMinDate(int year) {
        this.date = ToolDate.minDate(year);
        return this;
    }

    public DatePro toMinDate(int year, int month) {
        this.date = ToolDate.minDate(year, month);
        return this;
    }

    public DatePro toMinDate(int year, int month, int day) {
        this.date = ToolDate.minDate(year, month, day);
        return this;
    }

    public DatePro clone() {
        DatePro o = null;

        try {
            o = (DatePro) super.clone();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return o;
    }

    public String toString() {
        return this.toString("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    public String toString(String format) {
        return ToolDate.toFormat(format, this.date);
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

