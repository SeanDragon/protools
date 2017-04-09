package sd;

import org.junit.Test;
import pro.tools.http.HttpReceive;
import pro.tools.http.HttpSend;
import pro.tools.http.ToolHttp;

/**
 * http测试
 *
 * @author SeanDragon
 *         Create By 2017-04-07 9:30
 */
public class Test_20170407 {
    public boolean connect() {
        long l = System.currentTimeMillis();
        HttpSend httpSend = new HttpSend("http://a/5.com", null);
        //HttpSend httpSend = new HttpSend("http://m.tuhaolicai.cc/public/index", null);
        //HttpSend httpSend = new HttpSend("https://apio.caiyunapp.com/v2/Dz=JqbrtH7cP8SKd/121.6544,25.1552/realtime.json", null);
        //httpSend.setConnectTimeout(50);
        //httpSend.setResponseTimeout(50);
        HttpReceive httpReceive = ToolHttp.sendHttp(httpSend);
        if (httpReceive.isHaveError()) {
            System.out.println(httpReceive.getErrMsg());
            return false;
        } else {
            //System.out.println(httpReceive.getResponse().isRedirected());
            System.out.println(httpReceive.getStatusCode());
            //System.out.println(httpReceive.getStatusText());
            //String responseBody = httpReceive.getResponseBody();
            //System.out.println(responseBody);
            return true;
        }

        //long x = System.currentTimeMillis() - l;
        //System.out.println(x / 1000);
    }

    @Test
    public void test2() {
        long l = System.currentTimeMillis();

        final int[] falseCount = {0};
        for (int i = 0; i < 100; i++) {
            falseCount[0] += connect() ? 0 : 1;
        }

        System.out.println(falseCount[0]);

        System.out.println(System.currentTimeMillis() - l);
    }
}
