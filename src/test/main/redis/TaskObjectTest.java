package redis;

import org.junit.Test;
import pro.mojo.redis.dao.RedisAdapter;
import pro.mojo.redis.util.IDGenerator;
import pro.mojo.redis.lock.ILock;
import redis.clients.jedis.Jedis;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by steven on 2016/11/25.
 */
public class TaskObjectTest {

    private static Set<String> generatedIds = new HashSet<String>();

    private static final String LOCK_KEY = "lock.lock";
    private static final long LOCK_EXPIRE = 5 * 1000;

    @Test
    public void testV1_0() throws Exception {

        IDGenerator g1 = new IDGenerator();
        IDConsumeTask consume1 = new IDConsumeTask(g1, "consume1");

        IDGenerator g2 = new IDGenerator();
        IDConsumeTask consume2 = new IDConsumeTask(g2, "consume2");

        Thread t1 = new Thread(consume1);
        Thread t2 = new Thread(consume2);
        t1.start();
        t2.start();

        Thread.sleep(5 * 1000);

        IDConsumeTask.stopAll();

        t1.join();
        t2.join();
    }

    static String time() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    static class IDConsumeTask implements Runnable {

        private IDGenerator idGenerator;

        private String name;

        private static volatile boolean stop;

        public IDConsumeTask(IDGenerator idGenerator, String name) {
            this.idGenerator = idGenerator;
            this.name = name;
        }

        public static void stopAll() {
            stop = true;
        }

        public void run() {
            System.out.println(time() + ": consume " + name + " start ");
            while (!stop) {
                SocketAddress addr = new InetSocketAddress("localhost", 9091);
                Jedis jedis1 = null;
                try {
                    jedis1 = RedisAdapter.getHost("redis-lock").getRedis();
                } catch (Exception e) {
                    e.printStackTrace();
                    stopAll();
                    break;
                }
                ILock lock1 = null;
//                try {
//                    lock1 = new RedisLock(jedis1, LOCK_KEY, LOCK_EXPIRE, addr);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    continue;
//                }
                if(lock1 == null)
                    continue;
                if (lock1.tryLock(3, TimeUnit.SECONDS)) {
                    String id = idGenerator.getAndIncrement0();
                    if (id != null) {
                        if (generatedIds.contains(id)) {
                            System.out.println(time() + ": duplicate id generated, id = " + id);
                            stop = true;
                            continue;
                        }
                        generatedIds.add(id);
                        System.out.println(time() + ": consume " + name + " add id = " + id);
                    }
                    lock1.unlock();
                }
                lock1.release();
            }
            // 释放资源
//            idGenerator.release();
//            System.out.println(time() + ": consume " + name + " done ");
        }

    }
}
