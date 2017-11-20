package http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import pro.tools.constant.StrConst;
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

    @Test
    public void test2() {
        ByteBuf buffer = Unpooled.wrappedBuffer("123".getBytes());
        System.out.println(buffer.toString(StrConst.DEFAULT_CHARSET));
    }
}
