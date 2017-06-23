package sd.http.netty;

import org.junit.Test;
import pro.tools.http.netty.clientpool.ClientPool;
import pro.tools.http.netty.clientpool.NettyClientPool;

/**
 * @author SeanDragon Create By 2017-06-23 15:17
 */
public class Test_20170623 {
    @Test
    public void test1() {
        ClientPool clientPool = new NettyClientPool(100, "192.168.15.100", 91);
        clientPool.start();

        while (true) ;
    }
}
