package sd.http;

import org.junit.Test;
import pro.tools.http.ToolHttp;
import pro.tools.http.ToolHttpReceive;
import pro.tools.http.ToolHttpSend;

/**
 * 测试http发送工具类
 * Created by tuhao on 2017/3/31.
 */
public class HttpTest {

    @Test
    public void test2() {

        ToolHttpSend toolHttpSend = new ToolHttpSend("http://localhost/sd", null);
//        ToolHttpSend toolHttpSend = new ToolHttpSend("http://www.baidu.com", null);
//        pro.tools.http.ToolHttpSend toolHttpSend = new ToolHttpSend("http://www.baidu.com", null);
//        ToolHttpSend toolHttpSend = new ToolHttpSend("http://m.tuhaolicai.cc/public/index", null);
        //ToolHttpSend toolHttpSend = new ToolHttpSend("https://apio.caiyunapp.com/v2/Dz=JqbrtH7cP8SKd/121.6544,25.1552/realtime.json", null);
        //toolHttpSend.setConnectTimeout(50);
        //toolHttpSend.setResponseTimeout(50);
        ToolHttpReceive toolHttpReceive = ToolHttp.sendHttp(toolHttpSend);
        if (toolHttpReceive.isHaveError()) {
            System.out.println(toolHttpReceive.getErrMsg());
        } else {
            System.out.println(toolHttpReceive.getResponse().isRedirected());
            System.out.println(toolHttpReceive.getStatusCode());
            System.out.println(toolHttpReceive.getStatusText());
            String responseBody = toolHttpReceive.getResponseBody();
            System.out.println(responseBody);
        }
    }


    @Test
    public void test() {

//        System.err.println(HttpSendUtil.httpRequest("http://localhost:8888/home","GET"));
//        System.err.println(HttpSendUtil.httpRequest("http://localhost:8888/http","GET"));
//        Map str = pro.cg.convert.JsonToMap(HttpSendUtil.httpRequest("http://localhost:8080/sd?a=12&b=13","GET").toString());
//        Map str = pro.cg.convert.JsonToMap(HttpSendUtil.httpRequest("http://localhost/sd?a=12&b=13","GET").toString());
        ToolHttpSend send = new ToolHttpSend("http://localhost/sd?a=12&b=13", null);
        ToolHttpReceive httpReceive = ToolHttp.sendHttp(send);
//        Map str = pro.cg.convert.JsonToMap(HttpSendUtil.httpRequest("http://www.baidu.c","GET").toString());

        System.out.println(httpReceive);
//        System.out.println(str);

//        System.err.println(str.get("fa"));
//        System.err.println(str.get("abc"));
//        System.err.println(str.get("ab"));
//        System.err.println(str.get("a"));
//        System.out.println(str.get("list"));
//        System.out.println(str.get("result"));
//        System.out.println(pro.cg.convert.ModelToMap(str.get("abc")));

//        List<String> list = (List<String>)str.get("abc");
//
//        System.out.println(list);
//        for (String stc :list) {
////            Double st = (Double) stc;
//            System.err.print(stc+" ");
//        }
//        System.err.println(HttpSendUtil.httpRequest("http://www.baidu.com","GET"));
//        System.err.println("123456");
    }
}
