package pro.tools.http.jdk;

import com.google.common.collect.Maps;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.data.text.ToolJson;
import pro.tools.format.ToolFormat;
import pro.tools.http.pojo.HttpException;
import pro.tools.http.pojo.HttpMethod;
import pro.tools.http.pojo.HttpReceive;
import pro.tools.http.pojo.HttpScheme;
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
            if (HttpScheme.HTTP.equalsIgnoreCase(scheme)) {
                port = 80;
            } else if (HttpScheme.HTTPS.equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        Map<String, Object> param = send.getParams();
        Map<String, Object> sendHeaders = send.getHeaders();
        HttpMethod method = send.getMethod();
        Charset charset = send.getCharset();

        AsyncHttpClient asyncHttpClient = httpBuilder.buildDefaultClient();

        RequestBuilder builder = new RequestBuilder(method.name());

        AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.prepareRequest(builder.build());

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

        if (sendHeaders != null) {
            sendHeaders.forEach((key, value) -> requestBuilder.addHeader(key, value.toString()));
        }

        // TODO: 2017/7/27 Cookie未加入

        ListenableFuture<Response> future = requestBuilder.execute();

        try {
            Response response = future.get();

            Map<String, String> responseHeaderMap = Maps.newHashMap();
            if (send.getNeedReceiveHeaders()) {
                FluentCaseInsensitiveStringsMap responseHeaders = response.getHeaders();
                responseHeaders.forEach((k, v) -> {
                    if (v.size() == 1) {
                        responseHeaderMap.put(k, v.get(0));
                    } else {
                        responseHeaderMap.put(k, ToolJson.anyToJson(v));
                    }
                });
            }

            int responseStatusCode = response.getStatusCode();
            if (responseStatusCode != 200) {
                throw new HttpException("本次请求响应码不是200，是" + responseStatusCode);
            }
            String responseBody = response.getResponseBody();
            if (log.isDebugEnabled()) {
                log.debug(responseBody);
            }
            httpReceive.setStatusCode(responseStatusCode)
                    .setStatusText(response.getStatusText())
                    .setResponseBody(responseBody)
                    .setResponseHeader(responseHeaderMap)
                    .setHaveError(false)
            ;
        } catch (InterruptedException e) {
            httpReceive.setErrMsg("http组件出现问题!")
                    .setThrowable(e);
        } catch (IOException e) {
            httpReceive.setErrMsg("获取返回内容失败!")
                    .setThrowable(e);
        } catch (ExecutionException e) {
            httpReceive.setErrMsg("访问URL失败!")
                    .setThrowable(e);
        } catch (HttpException e) {
            httpReceive.setErrMsg(e.getMessage())
                    .setThrowable(e);
        }

        if (httpReceive.getHaveError()) {
            if (log.isWarnEnabled()) {
                Throwable throwable = httpReceive.getThrowable();
                log.warn(ToolFormat.toException(throwable), throwable);
            }
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