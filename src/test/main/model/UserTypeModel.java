package model;

import org.bson.types.ObjectId;
import pro.mojo.mongo.annotation.Collection;

/**
 * Created by sd on 17/3/1.
 */
@Collection(name = "bugpress_userType")
public class UserTypeModel implements java.io.Serializable {
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    private ObjectId _id;
    private Integer value;
    private String text;
    private Integer homePage;

    @Override
    public String toString() {
        return "UserTypeModel{" +
                "_id=" + _id +
                ", value=" + value +
                ", text='" + text + '\'' +
                ", homePage=" + homePage +
                '}';
    }

    public Integer getHomePage() {
        return homePage;
    }

    public void setHomePage(Integer homePage) {
        this.homePage = homePage;
    }


    public Integer getValue() {
        return value;
    }

    public UserTypeModel setValue(Integer value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public UserTypeModel setText(String text) {
        this.text = text;
        return this;
    }
}
