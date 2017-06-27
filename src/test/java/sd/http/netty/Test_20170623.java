package sd.http.netty;

import org.junit.Test;
import pro.tools.constant.HttpConst;
import pro.tools.http.netty.clientpool.ClientPool;
import pro.tools.http.netty.clientpool.NettyClientPool;

/**
 * @author SeanDragon Create By 2017-06-23 15:17
 */
public class Test_20170623 {
    private static final ClientPool clientPool = new NettyClientPool(100, "baidu.com", HttpConst.HttpScheme.HTTPS);

    @Test
    public void test1() {
        clientPool.start();
        while (true) ;
    }

    @Test
    public void test2() {

    }
}
