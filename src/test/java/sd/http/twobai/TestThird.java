package sd.http.twobai;

import junit.framework.TestCase;
import pro.tools.http.netty.clientpool.DefaultClientPool;
import pro.tools.http.netty.exception.HttpException;
import pro.tools.http.netty.pojo.HttpReceive;
import pro.tools.http.netty.pojo.HttpSend;
import pro.tools.system.ToolThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 19:13
 */
public class TestThird extends TestCase {

    private static final HttpSend httpSend = new HttpSend("/", null);
    private static DefaultClientPool defaultClientPool;

    static {
        try {
            defaultClientPool = new DefaultClientPool("http://192.168.15.88:1234");
        } catch (HttpException e) {
            e.printStackTrace();
        }

    }

    public void test1() throws Exception {

        ToolThreadPool threadPool = new ToolThreadPool(ToolThreadPool.Type.CachedThread, 100);

        final int[] count = {0};
        final int[] errCount = {0};
        final int[] no200 = {0};
        final List<Long> times = new ArrayList<>(10000);
        final long[] total = {0};

        for (int i = 0; i < 100; i++) {
            threadPool.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    long begin = System.currentTimeMillis();

                    HttpReceive receive = null;
                    try {
                        receive = defaultClientPool.request(httpSend);
                    } catch (InterruptedException | ExecutionException ignored) {
                    }

                    long x = System.currentTimeMillis() - begin;
                    times.add(x);
                    total[0] += x;
                    ++count[0];

                    if (receive.getHaveError()) {
                        ++errCount[0];
                        System.err.print(receive.getErrMsg() + "\t");
                    }

                    if (receive.getStatusCode() != 200) {
                        ++no200[0];
                        System.err.print(receive.getStatusCode() + "\t");
                    }

                    if (count[0] % 100 == 0) {
                        System.out.println(count[0]);
                    }

                    if (count[0] == 900) {
                        System.out.println(receive.getResponseBody());
                        Object[] objects = times.toArray();
                        Arrays.sort(objects);
                        System.out.println(Arrays.toString(objects));
                        System.out.println("avg:\t" + (total[0] / objects.length));
                        System.out.println("errCount:\t" + errCount[0]);
                        System.out.println("no200:\t" + no200[0]);
                        System.exit(-1);
                    }
                }
            });
        }
        while (true) ;
    }

    public void test2() throws ExecutionException, InterruptedException {
        long begin = System.currentTimeMillis();
        HttpReceive receive = defaultClientPool.request(httpSend);
        System.out.println(System.currentTimeMillis() - begin);
        System.out.println(receive);
    }
}

