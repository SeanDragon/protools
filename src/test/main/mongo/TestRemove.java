package mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.mojo.mongo.dao.MongoAdapter;

/**
 * Created by steven on 2016/12/14.
 */
public class TestRemove extends SpringTest{

    @Test
    public void DeleteOne(){
        AccountModel query = new AccountModel();
        query.setName("name1");

        System.err.println(adapter.deleteOne(query));
    }

}
