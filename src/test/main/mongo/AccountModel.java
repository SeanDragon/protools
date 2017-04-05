package mongo;

import org.bson.types.ObjectId;

/**
 * Created by steven on 2016/12/14.
 */
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * Created by SeanDragon on 2016/12/15.
 */
public class AccountModel {
    private ObjectId _id;
    private String name;
    private Date addDate;
    private FastModel fastModel;

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

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public FastModel getFastModel() {
        return fastModel;
    }

    public void setFastModel(FastModel fastModel) {
        this.fastModel = fastModel;
    }
}