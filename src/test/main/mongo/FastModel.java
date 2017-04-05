package mongo;

import org.bson.types.ObjectId;
import pro.mojo.mongo.annotation.Collection;

import java.util.Date;
import java.util.List;

/**
 * Created by steven on 2016/12/13.
 */
public class FastModel {
    private ObjectId _id;
    private String name;
    private Long count;
    private Boolean state;
    private Date date;
    private List<SmallModel> list;
    private List<String> lists;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public List<SmallModel> getList() {
        return list;
    }

    public void setList(List<SmallModel> list) {
        this.list = list;
    }

    public List<String> getLists() {
        return lists;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
    }
}
