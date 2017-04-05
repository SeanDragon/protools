package mongo;

import model.TransferErrorSubmit;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.mojo.mongo.dao.MongoAdapter;
import pro.mojo.mongo.query.Convert;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by steven on 2016/12/14.
 */
public class TestFindOne extends SpringTest {

    @Test
    public void FindOne() throws Exception {
        System.err.println(adapter.findOne("{}").json(FastModel.class));
    }

    @Test
    public void FindOne_error(){
        System.err.println(adapter.findOne(new TransferErrorSubmit()).get_id());
    }
}
