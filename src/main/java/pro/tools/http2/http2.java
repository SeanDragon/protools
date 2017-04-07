package pro.tools.http2;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;
import pro.tools.future.RegexUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

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

    public static ResponseBody sendHttp(RequestBody requestBody) {

        ResponseBody responseBody = new ResponseBody();
        responseBody.setHaveError(true);

        String url = requestBody.getUrl();
        if (!validURI(url)) {
            responseBody.setErrMsg("不是一个有效的URL");
            return responseBody;
        }
        Map<String, Object> param = requestBody.getParam();
        List<Cookie> cookies = requestBody.getCookieList();
        Map<String, Object> headers = requestBody.getHeaders();
        HTTP_METHOD method = requestBody.getMethod();

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder requestBuilder = null;

        switch (method) {
            case GET:
                requestBuilder = asyncHttpClient.prepareGet(url);
                break;
            case POST:
                requestBuilder = asyncHttpClient.preparePost(url);
                break;
            case PUT:
                requestBuilder = asyncHttpClient.preparePut(url);
                break;
            case DELETE:
                requestBuilder = asyncHttpClient.prepareDelete(url);
                break;
            case TRACE:
                requestBuilder = asyncHttpClient.prepareTrace(url);
                break;
        }
        if (param != null) {
            for (Map.Entry<String, Object> oneParam : param.entrySet()) {
                requestBuilder.addFormParam(oneParam.getKey(), oneParam.getValue().toString());
            }
        }

        if (headers != null) {
            for (Map.Entry<String, Object> oneHeader : headers.entrySet()) {
                requestBuilder.addHeader(oneHeader.getKey(), oneHeader.getValue().toString());
            }
        }

        if (cookies != null) {
            for (Cookie oneCookie : cookies) {
                requestBuilder.addCookie(oneCookie);
            }
        }

        ListenableFuture<Response> future = requestBuilder.execute();

        try {
            Response response = future.get(requestBody.getTimeout(), requestBody.getTimeUnit());
            List<Cookie> responseCookies = response.getCookies();
            responseBody.setResponseCookieList(responseCookies);
            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusText(response.getStatusText());
            if (requestBody.isNeedMsg()) {
                responseBody.setResponseBody(response.getResponseBody());
            }

            responseBody.setHaveError(false);

        } catch (InterruptedException | ExecutionException e) {
            responseBody.setErrMsg("访问URL失败!");
        } catch (TimeoutException e) {
            responseBody.setErrMsg("设置超时时间失败!");
        } catch (IOException e) {
            responseBody.setErrMsg("获取返回内容失败!");
        }
        return responseBody;
    }

    private static boolean validURI(String url) {
        try {
            URI uri = new URI(url);
            return RegexUtils.isURL(url);
        } catch (URISyntaxException e) {
            return false;
        }
    }

}