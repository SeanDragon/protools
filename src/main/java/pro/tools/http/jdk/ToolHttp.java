package pro.tools.http.jdk;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import pro.tools.data.text.ToolRegex;
import pro.tools.format.ToolFormat;
import pro.tools.http.pojo.HttpMethod;
import pro.tools.http.pojo.HttpReceive;
import pro.tools.http.pojo.HttpSend;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created on 17/4/6 22:06 星期四.
 *
 * @author SeanDragon
 */
public final class ToolHttp {
    private static HttpBuilder builder = new HttpBuilder();

    private ToolHttp() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static HttpReceive sendGet(String url) {
        HttpSend send = new HttpSend(url)
                .setMethod(HttpMethod.GET);
        return sendHttp(send);
    }

    public static HttpReceive sendPost(String url) {
        HttpSend send = new HttpSend(url)
                .setMethod(HttpMethod.POST);
        return sendHttp(send);
    }

    public static HttpReceive sendGet(String url, Map<String, Object> param) {
        HttpSend send = new HttpSend(url, param, HttpMethod.GET);
        return sendHttp(send);
    }

    public static HttpReceive sendPost(String url, Map<String, Object> param) {
        HttpSend send = new HttpSend(url, param, HttpMethod.POST);
        return sendHttp(send);
    }

    /**
     * 用于请求http
     *
     * @param send
     *         里面包含请求的信息
     *
     * @return 响应的信息
     */
    public static HttpReceive sendHttp(HttpSend send) {

        HttpReceive httpReceive = new HttpReceive();
        httpReceive.setHaveError(true);

        String url = send.getUrl();
        if (!ToolRegex.isURL(url)) {
            httpReceive.setErrMsg("不是一个有效的URL");
            return httpReceive;
        }

        Map<String, Object> param = send.getParams();
        Map<String, Object> headers = send.getHeaders();
        HttpMethod method = send.getMethod();
        Charset charset = send.getCharset();

        AsyncHttpClient asyncHttpClient = builder.buildDefaultClient();

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
        requestBuilder.setBodyEncoding(charset.toString());

        if (param != null) {
            param.forEach((key, value) -> requestBuilder.addQueryParam(key, value.toString()));
        }

        //设置基本请求头
        requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset.toString());

        if (headers != null) {
            headers.forEach((key, value) -> requestBuilder.addHeader(key, value.toString()));
        }

        // TODO: 2017/7/27 Cookie未加入

        ListenableFuture<Response> future = requestBuilder.execute();

        try {
            Response response = future.get();
            httpReceive.setStatusCode(response.getStatusCode());
            httpReceive.setStatusText(response.getStatusText());
            httpReceive.setResponseBody(response.getResponseBody());

            //将是否有错误信息设置为无
            httpReceive.setHaveError(false);

        } catch (InterruptedException e) {
            httpReceive.setErrMsg("http组件出现问题!\n" + ToolFormat.toException(e));
        } catch (IOException e) {
            httpReceive.setErrMsg("获取返回内容失败!\n" + ToolFormat.toException(e));
        } catch (ExecutionException e) {
            httpReceive.setErrMsg("访问URL失败!\n" + ToolFormat.toException(e));
        }

        return httpReceive;
    }

    public static HttpBuilder getBuilder() {
        return builder;
    }

    public static synchronized void setBuilder(HttpBuilder builder) {
        ToolHttp.builder = builder;
    }
}