package sd.http.twobai;

import junit.framework.TestCase;
import pro.tools.http.twobai.clientpool.TwobaiClientPool;
import pro.tools.http.twobai.exception.HttpException;
import pro.tools.http.twobai.listener.DisconnectListener;
import pro.tools.http.twobai.pojo.HttpReceive;
import pro.tools.http.twobai.pojo.HttpSend;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 9:28
 */
public class TestFirst extends TestCase {
    private static TwobaiClientPool clientPool;

    static {
        try {
            clientPool = new TwobaiClientPool("http://192.168.15.88:1234", 10, new Dis());
        } catch (HttpException e) {
            System.out.println("HttpException");
        }
    }

    public void testStart() throws InterruptedException {
        HttpSend httpSend = new HttpSend("/", null);
        HttpReceive httpReceive = clientPool.request(httpSend);
        if (httpReceive.getHaveError()) {
            System.out.print(httpReceive.getErrMsg() + "\t");
        } else {
            System.out.print(httpReceive.getStatusCode() + "\t");
        }

        clientPool.stop();
    }
}

class Dis implements DisconnectListener {

    @Override
    public void doSomething() {
        System.out.println("+-*//*--+++++++++++++++++++++++++++++++++++++++++++++++");
    }
}