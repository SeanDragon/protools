package sd.http.netty;

import org.junit.Test;
import pro.tools.constant.HttpConst;
import pro.tools.http.netty.clientpool.ClientPool;
import pro.tools.http.netty.clientpool.NettyClientPool;
import pro.tools.http.netty.future.Future;
import pro.tools.http.netty.http.Request;
import pro.tools.http.netty.http.Response;
import pro.tools.system.ToolThreadPool;

import java.nio.charset.Charset;

/**
 * @author SeanDragon Create By 2017-06-22 9:49
 */
public class Test_20170622 {

    private static final ClientPool pool = new NettyClientPool(100, "192.168.15.20", 8088);
    private static int count = 0;

    /**
     * 异步调用
     *
     * @param path
     * @param method
     */
    public static void doGet(String path, HttpConst.RequestMethod method) {
        Request request = new Request(path, method);
        pool.request(request).addListener(new Future.Listener() {
            @Override
            public void complete(Object arg) {
                Response response = (Response) arg;
                String result = response.getBody().toString(Charset.forName("utf-8"));
                //System.out.println(++count + "\t" + result.length());
                System.out.println("Test_20170622.complete");
                System.out.println(result);
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
    //    Request request = new Request("/public/index", HttpConst.RequestMethod.GET);
    //    request.getBody().writeBytes("aa".getBytes(Charset.forName("utf-8")));
    //    Response response = pool.requestWithTimeOut(request, 2000).sync();
    //    pool.stop();
    //    System.out.println(response.getBody().toString(Charset.forName("utf-8")));
    //}

    public static void main(String args[]) throws Throwable {
        //long b = System.currentTimeMillis();
        pool.start();
        final ToolThreadPool threadPool = new ToolThreadPool(ToolThreadPool.Type.CachedThread, 50);
        //final int[] count = {0};
        //final List<Long> times = new ArrayList<>(1000);
        //final long[] total = {0};

        for (int i = 0; i < 50; i++) {
            threadPool.submit(() -> {
                for (int j = 0; j < 20; j++) {
                    //long begin = System.currentTimeMillis();
                    doGet("/getService", HttpConst.RequestMethod.GET);
                    //doGet("/wp-includes/js/wp-embed.min.js?ver=4.7.5", HttpConst.RequestMethod.GET);
                    //doGet("/public/index/", HttpConst.RequestMethod.GET);
                    //doGet("/#ie=UTF-8&wd=SeanDragon", HttpConst.RequestMethod.GET);
                    //long x = System.currentTimeMillis() - begin;
                    //times.add(x);
                    //total[0] += x;
                    //++count[0];
                    //System.out.println(count[0]);
                    //if (count[0] == 850) {
                    //    Object[] objects = times.toArray();
                    //    Arrays.sort(objects);
                    //    System.out.println(Arrays.toString(objects));
                    //    System.out.println("avg:\t" + (total[0] / objects.length) / 1000D);
                    //    System.out.println((System.currentTimeMillis() - b) / 1000D);
                    //    System.exit(-1);
                    //}
                }
            });
        }
    }

    @Test
    public void test1() {
        pool.start();
        doGet("/getService", HttpConst.RequestMethod.GET);
        doGet("/getService", HttpConst.RequestMethod.GET);
        doGet("/getService", HttpConst.RequestMethod.GET);
        while (true) ;
    }
}
