package mongo;

import mongo.so;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.mojo.mongo.dao.MongoAdapter;
import pro.mojo.redis.time.TimeServer;

/**
 * Created by steven on 2016/12/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mojo.xml")
public class SpringTest {
    protected MongoAdapter adapter;

    @Before
    public void init() throws Exception {
        TimeServer.timeServer.start();
        System.out.println("TimeServer started!");
        adapter = so.tuhaoAdapter();
    }

    @After
    public void destory(){
        TimeServer.timeServer.stop();
        System.out.println("TimeServer stoped!");
    }
}
