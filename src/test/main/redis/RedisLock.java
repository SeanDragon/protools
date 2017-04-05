package redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.mojo.redis.dao.IRedisAdapter;
import pro.mojo.redis.dao.RedisAdapter;
import pro.mojo.redis.lock.ILock;
import pro.mojo.redis.time.TimeClient;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * Created by steven on 2016/12/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mojo.xml")
public class RedisLock {
    @Test
    public void test() throws Exception {
        while (true) {
            IRedisAdapter lockAdapter = null;
            ILock lock1 = null;
            try {
                lockAdapter = RedisAdapter.selectHost("redis-lock");
                lock1 = new pro.mojo.redis.lock.RedisLock(lockAdapter, "lock:loanNo###", 5 * 1000, TimeClient.timeClient);
                if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println("Thread 1 locked");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock1.unlock();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (lock1 != null) {
                    lock1.release();
                }
            }
            Thread.sleep(1000);
        }
    }
    @Test
    public void Test_RedisLock() throws InterruptedException {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    IRedisAdapter lockAdapter = null;
                    ILock lock1 = null;
                    try {
                        lockAdapter = RedisAdapter.selectHost("redis-lock");
                        lock1 = new pro.mojo.redis.lock.RedisLock(lockAdapter, "lock:loanNo###", 5 * 1000, TimeClient.timeClient);
                        if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                            System.out.println("Thread 1 locked");
                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if (lock1 != null) {
                            lock1.release();
                        }
                    }
                }
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    IRedisAdapter lockAdapter = null;
                    ILock lock1 = null;
                    try {
                        lockAdapter = RedisAdapter.selectHost("redis-lock");
                        lock1 = new pro.mojo.redis.lock.RedisLock(lockAdapter, "lock:loanNo###", 5 * 1000, TimeClient.timeClient);
                        if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                            System.out.println("Thread 2 locked");
                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if (lock1 != null) {
                            lock1.release();
                        }
                    }
                }
            }
        };

        Thread t1 = new Thread(r1);
//        Thread t2 = new Thread(r2);

        t1.start();
//        t2.start();

        t1.join();
//        t2.join();
    }
}
