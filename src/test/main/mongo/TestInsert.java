package mongo;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.mojo.mongo.dao.MongoAdapter;
import pro.mojo.redis.time.TimeClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by steven on 2016/12/14.
 */
public class TestInsert extends SpringTest{

    @Test
    public void InsertOne(){
        SmallModel sm = new SmallModel();
        sm.setName("a");
        sm.setDate(so.nowDate());

        List<SmallModel> slist = new LinkedList<>();
        slist.add(sm);

        List<String> listString = new ArrayList<>();
        listString.add("a");
        listString.add("b");

        AccountModel accountModel = new AccountModel();
        accountModel.set_id(new ObjectId());
        accountModel.setName("name");
        accountModel.setAddDate(so.nowDate());

        FastModel t = new FastModel();
        t.setName("name");
        t.set_id(new ObjectId());
        t.setCount(1234l);
        t.setState(true);
        t.setDate(so.nowDate());
        t.setList(slist);
        t.setLists(listString);

        accountModel.setFastModel(t);

        adapter.insertOne(accountModel);
    }

    @Test
    public void InsertList(){

        SmallModel sm = new SmallModel();
        sm.setName("a");
        sm.setDate(so.nowDate());

        SmallModel sm2 = new SmallModel();
        sm2.setName("a");
        sm2.setDate(so.nowDate());

        List<AccountModel> list = new LinkedList<>();
        List<SmallModel> slist = new LinkedList<>();
        slist.add(sm);
        slist.add(sm2);

        List<String> listString = new ArrayList<>();
        listString.add("a");
        listString.add("b");



        for(int i=0;i<13;i++) {
            AccountModel accountModel = new AccountModel();
            accountModel.set_id(new ObjectId());
            accountModel.setName("name"+i);
            accountModel.setAddDate(so.nowDate());

            FastModel t = new FastModel();
            t.setName("name"+i);
            t.set_id(new ObjectId());
            t.setCount(1234l);
            t.setState(true);
            t.setDate(so.nowDate());
            t.setList(slist);
            t.setLists(listString);

            accountModel.setFastModel(t);

            list.add(accountModel);
        }
        adapter.insertList(list);
    }

    @Test
    public void TestJson(){
        FastModel model = new FastModel();
        model.set_id(new ObjectId());
        model.setName("name13");
//        model.setCount(1234l);
        model.setState(true);
        model.setDate(so.nowDate());
        Document doc = new Document(pro.tools.convert.ModelToMap(model));
        System.err.println(doc.toJson());
    }
}
