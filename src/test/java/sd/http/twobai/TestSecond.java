package sd.http.twobai;

import pro.tools.http.twobai.clientpool.TwobaiClientPool;
import pro.tools.http.twobai.exception.HttpException;
import pro.tools.http.twobai.pojo.HttpReceive;
import pro.tools.http.twobai.pojo.HttpSend;
import pro.tools.system.ToolThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 18:08
 */
public class TestSecond {

    private static TwobaiClientPool clientPool;

    static {
        try {
            //clientPool = new TwobaiClientPool("http://192.168.15.27", 1000, new Dis());
            clientPool = new TwobaiClientPool("http://192.168.15.92", 10, new Dis());
        } catch (HttpException e) {
            System.out.println("HttpException");
        }
    }

    public static void main(String[] args) {
        HttpSend httpSend = new HttpSend("/getService", null);

        ToolThreadPool threadPool = new ToolThreadPool(ToolThreadPool.Type.CachedThread, 100);

        final int[] count = {0};
        final int[] errCount = {0};
        final List<Long> times = new ArrayList<>(10000);
        final long[] total = {0};

        for (int i = 0; i < 100; i++) {
            threadPool.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    long begin = System.currentTimeMillis();

                    HttpReceive receive = clientPool.request(httpSend);

                    long x = System.currentTimeMillis() - begin;
                    times.add(x);
                    total[0] += x;
                    ++count[0];

                    if (receive.getHaveError()) {
                        ++errCount[0];
                        System.err.print(receive.getErrMsg() + "\t");
                    }

                    if (count[0] == 900) {
                        System.out.println(receive.getResponseBody());
                        Object[] objects = times.toArray();
                        Arrays.sort(objects);
                        System.out.println(Arrays.toString(objects));
                        System.out.println("avg:\t" + (total[0] / objects.length));
                        System.out.println("errCount:\t" + errCount[0]);
                        System.exit(-1);
                    }
                }
            });
        }
    }
}
