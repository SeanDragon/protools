package sd;

import org.junit.Test;
import pro.tools.http.ToolHttp;
import pro.tools.http.ToolHttpReceive;
import pro.tools.http.ToolHttpSend;

/**
 * http测试
 *
 * @author SeanDragon
 *         Create By 2017-04-07 9:30
 */
public class Test_20170407 {
    public boolean connect() {
        long l = System.currentTimeMillis();
        ToolHttpSend toolHttpSend = new ToolHttpSend("http://a/5.com", null);
        //ToolHttpSend toolHttpSend = new ToolHttpSend("http://m.tuhaolicai.cc/public/index", null);
        //ToolHttpSend toolHttpSend = new ToolHttpSend("https://apio.caiyunapp.com/v2/Dz=JqbrtH7cP8SKd/121.6544,25.1552/realtime.json", null);
        //toolHttpSend.setConnectTimeout(50);
        //toolHttpSend.setResponseTimeout(50);
        ToolHttpReceive toolHttpReceive = ToolHttp.sendHttp(toolHttpSend);
        if (toolHttpReceive.isHaveError()) {
            System.out.println(toolHttpReceive.getErrMsg());
            return false;
        } else {
            //System.out.println(toolHttpReceive.getResponse().isRedirected());
            System.out.println(toolHttpReceive.getStatusCode());
            //System.out.println(toolHttpReceive.getStatusText());
            //String responseBody = toolHttpReceive.getResponseBody();
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
