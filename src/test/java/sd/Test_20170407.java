package sd;

import com.ning.http.client.Response;
import org.junit.Test;
import pro.tools.http2.HTTP_METHOD;
import pro.tools.http2.RequestBody;
import pro.tools.http2.ResponseBody;
import pro.tools.http2.http2;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * http测试
 *
 * @author SeanDragon
 *         Create By 2017-04-07 9:30
 */
public class Test_20170407 {
    @Test
    public void test1() {
        RequestBody requestBody = new RequestBody("http2://商宇龙.com", null, HTTP_METHOD.POST);
        ResponseBody responseBody = http2.sendHttp(requestBody);
        if (responseBody.isHaveError()) {
            System.out.println(responseBody.getErrMsg());
        } else {
            System.out.println(responseBody.getStatusCode());
            System.out.println(responseBody.getStatusText());
            System.out.println(responseBody.getResponseBody());
        }
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException, IOException {
        Response response = http2.sendHttpPost("http2://m.tuhaolicai.cc/public/index", null);
        String responseBody = response.getResponseBody();
        System.out.println(responseBody);
    }
}
