package queue;

import java.util.Date;

/**
 * Created by steven on 2016/8/30.
 */
public class Log {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
    private String value;

}
