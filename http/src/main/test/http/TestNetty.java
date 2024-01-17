package http;

import org.junit.Test;
import pro.tools.http.netty.clientpool.DefaultClientPool;
import pro.tools.http.pojo.HttpException;
import pro.tools.http.pojo.HttpMethod;
import pro.tools.http.pojo.HttpReceive;
import pro.tools.http.pojo.HttpSend;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-11-28 16:46
 */
public class TestNetty {
    static DefaultClientPool defaultClientPool;

    @Test
    public void test1() throws HttpException {
        defaultClientPool = new DefaultClientPool("http://localhost:92");

        int count = 1000;
        AtomicInteger finishCount = new AtomicInteger();
        AtomicInteger errCount = new AtomicInteger();
        AtomicInteger okCount = new AtomicInteger();

        long begin = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Thread newThread = new Thread(() -> {
                HttpReceive receive = defaultClientPool.request(HttpSend.of("/getService").setMethod(HttpMethod.POST),10, TimeUnit.SECONDS);
                // HttpReceive receive = ToolSendHttp.post("http://localhost:92/getService");
                Boolean haveError = receive.getHaveError();
                if (haveError || receive.getStatusCode() != 200) {
                    System.out.println(finishCount.incrementAndGet() + "-error");
                    System.out.println(receive.getErrMsg());
                    errCount.incrementAndGet();
                } else {
                    System.out.println(finishCount.incrementAndGet() + "-ok");
                    okCount.incrementAndGet();
                }
            });
            newThread.start();
        }

        while (finishCount.get() < count) ;
        System.out.println("ERROR:\t\t" + errCount.get());
        System.out.println("OK:\t\t" + okCount.get());
        System.out.println("TIME:\t\t" + (System.currentTimeMillis() - begin) / 1000D + "秒");
        DefaultClientPool.stopAll();
        while (true);
    }

    @Test
    public void test2() {
        StringBuilder stringBuilder = new StringBuilder("123123");
        stringBuilder.deleteCharAt(stringBuilder.length()-1);

        System.out.println(stringBuilder);
        System.out.println(stringBuilder.toString());
    }
}
