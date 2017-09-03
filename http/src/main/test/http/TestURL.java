package http;

import org.junit.Test;
import pro.tools.http.okhttp.ToolSendHttp;
import pro.tools.http.pojo.HttpSend;

/**
 * Created on 17/9/3 13:12 星期日.
 *
 * @author sd
 */
public class TestURL {
    @Test
    public void test1() {
        HttpSend httpSend = new HttpSend("https://baidu.com");
        ToolSendHttp.get(httpSend);
    }
}
