package http;

import org.junit.Test;
import pro.tools.http.okhttp.ToolSendHttp;
import pro.tools.http.pojo.HttpReceive;
import pro.tools.http.pojo.HttpSend;

/**
 * Created on 17/9/3 13:12 星期日.
 *
 * @author sd
 */
public class TestURL {
    @Test
    public void test1() {
        String url = "https://baidu.com";
        url = "http://192.168.15.100:92/getService";
        HttpSend httpSend = new HttpSend(url);
        HttpReceive send = ToolSendHttp.send(httpSend);
        System.out.println(send);
    }
}
