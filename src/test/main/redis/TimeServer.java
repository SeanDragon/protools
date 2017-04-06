package redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.tools.date;
import pro.mojo.redis.dao.RedisAdapter;
import pro.mojo.redis.time.TimeClient;
import redis.clients.jedis.Jedis;

/**
 * Created by steven on 2016/12/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mojo.xml")
public class TimeServer {
    @Test
    public void Test_TimeServer() throws Exception {
        pro.mojo.redis.time.TimeServer.timeServer.start();
        while(true)
            Thread.sleep(1000);
    }

    @Test
    public void Test_TimeClient() throws InterruptedException {
        pro.mojo.redis.time.TimeServer.timeServer.start();
        while(true) {
//            Thread.sleep(1000);
            System.out.println(TimeClient.getDatePro().toString(date.format.hms));
        }
    }

    @Test
    public void test() throws Exception {
        RedisAdapter adapter = RedisAdapter.selectHost("redis-data");
        while(true) {
            Jedis j1 = adapter.getHost().getRedis();
            System.out.println((j1==null) + j1.ping());
            adapter.release(j1);
        }
    }

    @Test
    public void test2() throws Exception {
        RedisAdapter adapter = RedisAdapter.selectHost("redis-data");
        Jedis j1 = adapter.getHost().getRedis();
        System.err.println(j1.ping());
        adapter.release(j1);
        while(true);
    }

}
