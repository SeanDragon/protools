package mongo;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.mojo.mongo.dao.MongoAdapter;
import pro.mojo.mongo.dao.Update;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by steven on 2016/12/14.
 */
public class TestUpdate extends SpringTest{

    @Test
    public void UpdateOne(){
        List<String> slist = new LinkedList<>();
        slist.add("a");
        slist.add("b");
        slist.add("c");

        AccountModel query = new AccountModel();
        query.setName("name2");

        AccountModel one = adapter.findOne(query);

        System.err.println("[find]"+pro.cg.convert.ModelToBson(one));

        SmallModel sm = new SmallModel();
        sm.setName("a");
        sm.setDate(so.nowDate());

        one.getFastModel().getList().add(sm);
        one.getFastModel().setName("name2");

        Update.Result result = adapter.updateOne(query,one);
        System.err.println(result.getMatchedCount());
        System.err.println(result.getModifiedCount());

//        AccountModel accountModel = new AccountModel();
//        accountModel.set_id(new ObjectId());
//        accountModel.setName("name");
//        accountModel.setAddDate(so.nowDate());
//
//        FastModel t = new FastModel();
//        t.setName("name");
//        t.set_id(new ObjectId());
//        t.setCount(1234l);
//        t.setState(true);
//        t.setDate(so.nowDate());
//        t.setList(slist);
//
//        accountModel.setFastModel(t);
//
//        adapter.insertOne(accountModel);
    }


}
