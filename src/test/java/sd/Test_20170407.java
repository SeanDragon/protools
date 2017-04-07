package sd;

import org.junit.Test;
import pro.tools.http2.HTTP_METHOD;
import pro.tools.http2.HttpRequest;
import pro.tools.http2.HttpResponse;
import pro.tools.http2.http2;

/**
 * http测试
 *
 * @author SeanDragon
 *         Create By 2017-04-07 9:30
 */
public class Test_20170407 {
    @Test
    public void test1() {
        HttpRequest httpRequest = new HttpRequest("https://www.zhihu.com/", null, HTTP_METHOD.POST);
        HttpResponse httpResponse = http2.sendHttp(httpRequest);
        if (httpResponse.isHaveError()) {
            System.out.println(httpResponse.getErrMsg());
        } else {
            System.out.println(httpResponse.getStatusCode());
            System.out.println(httpResponse.getStatusText());
            System.out.println(httpResponse.getResponseBody());
        }
    }
}
