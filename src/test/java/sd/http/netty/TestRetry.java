package sd.http.netty;

import com.google.common.collect.Maps;
import junit.framework.TestCase;
import pro.tools.constant.StrConst;
import pro.tools.http.netty.clientpool.ClientPool;
import pro.tools.http.netty.clientpool.NettyClientPool;
import pro.tools.http.netty.http.Request;
import pro.tools.http.netty.http.RequestFuture;
import pro.tools.http.netty.http.Response;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-19 14:17
 */
public class TestRetry extends TestCase {
    private static final ClientPool clientPool = new NettyClientPool(10, "127.0.0.1", 92);

    public void test1() throws Throwable {
        clientPool.start();

        Request request = new Request("/getService");
        request.setParams(Maps.newHashMap());

        RequestFuture requestFuture = clientPool.requestWithTimeOut(request, 1000 * 3);
        Response sync = requestFuture.sync();
        System.out.println(sync.getBody().toString(StrConst.DEFAULT_CHARSET));
        System.out.println(sync.getResponse());
    }
}
