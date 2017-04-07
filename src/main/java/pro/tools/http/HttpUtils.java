package pro.tools.http;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;
import pro.tools.constant.SystemConstant;
import pro.tools.future.RegexUtils;
import pro.tools.tools;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created on 17/4/6 22:06 星期四.
 *
 * @author sd
 */
public class HttpUtils {

    /**
     * 用于请求http
     *
     * @param httpSend 里面包含请求的信息
     * @return 响应的信息
     */
    public static HttpReceive sendHttp(HttpSend httpSend) {

        HttpReceive httpReceive = new HttpReceive();
        httpReceive.setHaveError(true);

        String url = httpSend.getUrl();
        if (!RegexUtils.isURL(url)) {
            if (httpSend.isNeedErrMsg()) {
                httpReceive.setErrMsg("不是一个有效的URL");
            }
            return httpReceive;
        }
        Map<String, Object> param = httpSend.getParam();
        List<Cookie> cookies = httpSend.getCookieList();
        Map<String, Object> headers = httpSend.getHeaders();
        HTTP_METHOD method = httpSend.getMethod();

        AsyncHttpClientConfig.Builder builder = HttpBuilder.buildDefault();
        if (httpSend.isNeedConnectTimeout()) {
            builder.setConnectTimeout(httpSend.getConnectTimeout());
        }
        AsyncHttpClientConfig build = builder.build();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(build);
        AsyncHttpClient.BoundRequestBuilder requestBuilder;

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
            default:
                requestBuilder = asyncHttpClient.prepareGet(url);
                break;
        }

        //设置编码
        requestBuilder.setBodyEncoding(SystemConstant.DEFAULT_CHARSET);
        if (httpSend.isNeedResponseTimeout()) {
            requestBuilder.setRequestTimeout(httpSend.getResponseTimeout());
        }

        if (param != null) {
            for (Map.Entry<String, Object> oneParam : param.entrySet()) {
                requestBuilder.addQueryParam(oneParam.getKey(), oneParam.getValue().toString());
            }
        }

        //设置基本请求头
        requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + SystemConstant.DEFAULT_CHARSET);
        requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");

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
            Response response;
            if (httpSend.isNeedResponseTimeout()) {
                response = future.get(
                        httpSend.getResponseTimeout(), httpSend.getResponseTimeoutUnit()
                );
            } else {
                response = future.get();
            }
            httpReceive.setResponseCookieList(response.getCookies());
            httpReceive.setStatusCode(response.getStatusCode());
            httpReceive.setStatusText(response.getStatusText());
            if (httpSend.isNeedMsg()) {
                String responseBody = response.getResponseBody();
                responseBody = new String(responseBody.getBytes(), SystemConstant.DEFAULT_CHARSET);
                httpReceive.setResponseBody(responseBody);
            }

            //将原生对象放入,方便复杂操作
            httpReceive.setResponse(response);
            //将是否有错误信息设置为无
            httpReceive.setHaveError(false);

        } catch (InterruptedException e) {
            httpReceive.setErrMsg("http组件出现问题!\n" + tools.toException(e));
        } catch (TimeoutException e) {
            httpReceive.setErrMsg("设置超时时间失败!\n" + tools.toException(e));
        } catch (IOException e) {
            httpReceive.setErrMsg("获取返回内容失败!\n" + tools.toException(e));
        } catch (ExecutionException e) {
            //httpReceive.setErrMsg("访问URL失败!\n" + tools.toException(e));
            e.printStackTrace();
        }

        if (!httpSend.isNeedErrMsg()) {
            httpReceive.setErrMsg("");
        }

        return httpReceive;
    }

}