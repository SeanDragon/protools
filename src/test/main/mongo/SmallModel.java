package mongo;

import java.util.Date;

/**
 * Created by steven on 2016/12/20.
 */
public class SmallModel {
    private String name;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Date date;
}
