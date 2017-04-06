package pro.tools;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created on 17/4/6 22:06 星期四.
 *
 * @author sd
 */
public class http2 {

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static Response sendHttpGet(String url, Map<String, Object> param) throws ExecutionException, InterruptedException {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.prepareGet(url);

        param.entrySet().forEach(one -> {
            requestBuilder.addFormParam(one.getKey(), one.getValue().toString());
        });

        Future<Response> f = requestBuilder.execute();
        return f.get();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static Response sendHttpPost(String url, Map<String, Object> param) throws ExecutionException, InterruptedException {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.preparePost(url);

        param.entrySet().forEach(one -> {
            requestBuilder.addFormParam(one.getKey(), one.getValue().toString());
        });

        Future<Response> f = requestBuilder.execute();
        return f.get();
    }
}