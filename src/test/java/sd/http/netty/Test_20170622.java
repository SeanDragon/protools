package sd.http.netty;

import pro.tools.http.netty.clientpool.ClientPool;
import pro.tools.http.netty.clientpool.NettyClientPool;
import pro.tools.http.netty.future.Future;
import pro.tools.http.netty.http.Request;
import pro.tools.http.netty.http.Response;
import pro.tools.system.ToolThreadPool;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SeanDragon Create By 2017-06-22 9:49
 */
public class Test_20170622 {
    private static final ClientPool pool = new NettyClientPool(1000, "ss.ishadowx.com");

    /**
     * 异步调用
     *
     * @param path
     * @param method
     */
    public static void doGet(String path, Request.RequestMethod method) {
        Request request = new Request(path, method);
        pool.request(request).addListener(new Future.Listener() {
            @Override
            public void complete(Object arg) {
                Response res = (Response) arg;
                String result = res.getBody().toString(Charset.forName("utf-8"));
                System.out.print(result.length() + "\t");
            }

            @Override
            public void exception(Throwable t) {
                t.printStackTrace();
            }
        });

    }

    //public static void doGetSync() throws Throwable {
    //    final ClientPool pool = new NettyClientPool(2, "http://", "192.168.15.13");
    //    pool.start();
    //    Request request = new Request("/public/index", Request.RequestMethod.GET);
    //    request.getBody().writeBytes("aa".getBytes(Charset.forName("utf-8")));
    //    Response response = pool.requestWithTimeOut(request, 2000).sync();
    //    pool.stop();
    //    System.out.println(response.getBody().toString(Charset.forName("utf-8")));
    //}

    public static void main(String args[]) throws Throwable {
        long b = System.currentTimeMillis();
        pool.start();
        final ToolThreadPool threadPool = new ToolThreadPool(ToolThreadPool.Type.CachedThread, 1000);
        final int[] count = {0};
        final List<Long> times = new ArrayList<>(1000);
        final long[] total = {0};

        for (int i = 0; i < 50; i++) {
            threadPool.submit(() -> {
                for (int j = 0; j < 20; j++) {
                    long begin = System.currentTimeMillis();
                    doGet("/", Request.RequestMethod.GET);
                    //doGet("/public/index/", Request.RequestMethod.GET);
                    //doGet("/#ie=UTF-8&wd=SeanDragon", Request.RequestMethod.GET);
                    long x = System.currentTimeMillis() - begin;
                    times.add(x);
                    total[0] += x;
                    ++count[0];
                    //System.out.println(count[0]);
                    if (count[0] == 850) {
                        Object[] objects = times.toArray();
                        Arrays.sort(objects);
                        System.out.println(Arrays.toString(objects));
                        System.out.println("avg:\t" + (total[0] / objects.length) / 1000D);
                        System.out.println((System.currentTimeMillis() - b) / 1000D);
                        //System.exit(-1);
                    }
                }
            });
        }

    }
}
