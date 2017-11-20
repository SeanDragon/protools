package pro.tools.http.jdk;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.format.ToolFormat;
import pro.tools.http.pojo.HttpMethod;
import pro.tools.http.pojo.HttpReceive;
import pro.tools.http.pojo.HttpSend;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created on 17/4/6 22:06 星期四.
 *
 * @author SeanDragon
 */
public final class ToolHttp {
    private static final Logger log = LoggerFactory.getLogger(ToolHttp.class);
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
        return sendHttp(send, builder);
    }

    /**
     * 用于请求http
     *
     * @param send
     *         里面包含请求的信息
     *
     * @return 响应的信息
     */
    public static HttpReceive sendHttp(HttpSend send, HttpBuilder httpBuilder) {
        HttpReceive httpReceive = new HttpReceive();
        httpReceive.setHaveError(true);

        String url = send.getUrl();
        URI uri;
        try {
            uri = new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            httpReceive.setErrMsg("不是一个有效的URL")
                    .setThrowable(e);
            return httpReceive;
        }

        String scheme = uri.getScheme();
        String host = uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        Map<String, Object> param = send.getParams();
        Map<String, Object> headers = send.getHeaders();
        HttpMethod method = send.getMethod();
        Charset charset = send.getCharset();

        AsyncHttpClient asyncHttpClient = httpBuilder.buildDefaultClient();

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
        requestBuilder.addHeader(HttpHeaderNames.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED + ";charset" + charset.toString())
                .addHeader(HttpHeaderNames.CONNECTION.toString(), HttpHeaderValues.KEEP_ALIVE.toString())
                .addHeader(HttpHeaderNames.ACCEPT_ENCODING.toString(), HttpHeaderValues.GZIP_DEFLATE.toString())
                .addHeader(HttpHeaderNames.USER_AGENT.toString(), "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html）")
                .addHeader(HttpHeaderNames.CACHE_CONTROL.toString(), "max-age=0")
                .addHeader(HttpHeaderNames.HOST.toString(), host + ":" + port)
                .addHeader("DNT", "1")
        ;

        if (headers != null) {
            headers.forEach((key, value) -> requestBuilder.addHeader(key, value.toString()));
        }

        // TODO: 2017/7/27 Cookie未加入

        ListenableFuture<Response> future = requestBuilder.execute();

        try {
            Response response = future.get();
            int responseStatusCode = response.getStatusCode();
            if (responseStatusCode != 200) {
                httpReceive.setErrMsg("本次请求响应码不是200，是" + responseStatusCode)
                ;
            } else {
                httpReceive.setStatusCode(responseStatusCode)
                        .setStatusText(response.getStatusText())
                        .setResponseBody(response.getResponseBody())
                        .setHaveError(false)
                ;
            }
        } catch (InterruptedException e) {
            httpReceive.setErrMsg("http组件出现问题!")
                    .setThrowable(e);
            log.warn(ToolFormat.toException(e), e);
        } catch (IOException e) {
            httpReceive.setErrMsg("获取返回内容失败!")
                    .setThrowable(e);
            log.warn(ToolFormat.toException(e), e);
        } catch (ExecutionException e) {
            httpReceive.setErrMsg("访问URL失败!")
                    .setThrowable(e);
            log.warn(ToolFormat.toException(e), e);
        }

        httpReceive.setIsDone(true);

        return httpReceive;
    }

    public static HttpBuilder getBuilder() {
        return builder;
    }

    public static synchronized void setBuilder(HttpBuilder builder) {
        ToolHttp.builder = builder;
    }
}