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
import java.util.concurrent.TimeoutException;

/**
 * Created on 17/4/6 22:06 星期四.
 *
 * @author sd
 */
public class http2 {

    /**
     * 用于请求http
     *
     * @param httpRequest 里面包含请求的信息
     * @return 响应的信息
     */
    public static HttpResponse sendHttp(HttpRequest httpRequest) {

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHaveError(true);

        String url = httpRequest.getUrl();
        if (!validURI(url)) {
            if (httpRequest.isNeedErrMsg()) {
                httpResponse.setErrMsg("不是一个有效的URL");
            }
            return httpResponse;
        }
        Map<String, Object> param = httpRequest.getParam();
        List<Cookie> cookies = httpRequest.getCookieList();
        Map<String, Object> headers = httpRequest.getHeaders();
        HTTP_METHOD method = httpRequest.getMethod();

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

        requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
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
            Response response = future.get(httpRequest.getTimeout(), httpRequest.getTimeUnit());
            List<Cookie> responseCookies = response.getCookies();
            httpResponse.setResponseCookieList(responseCookies);
            httpResponse.setStatusCode(response.getStatusCode());
            httpResponse.setStatusText(response.getStatusText());
            if (httpRequest.isNeedMsg()) {
                String responseBody = response.getResponseBody();
                responseBody = new String(responseBody.getBytes(), "utf8");
                httpResponse.setResponseBody(responseBody);
            }

            httpResponse.setHaveError(false);

        } catch (InterruptedException | ExecutionException e) {
            httpResponse.setErrMsg("访问URL失败!");
        } catch (TimeoutException e) {
            httpResponse.setErrMsg("设置超时时间失败!");
        } catch (IOException e) {
            httpResponse.setErrMsg("获取返回内容失败!");
        }

        if (!httpRequest.isNeedErrMsg()) {
            httpResponse.setErrMsg("");
        }

        return httpResponse;
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