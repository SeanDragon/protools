package pro.tools.http.twobai.client;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringEncoder;
import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.http.jdk.Tool_HTTP_METHOD;
import pro.tools.http.twobai.clientpool.TwobaiClientPool;
import pro.tools.http.twobai.handler.HttpClientHandler;
import pro.tools.http.twobai.pojo.HttpReceive;
import pro.tools.http.twobai.pojo.HttpSend;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 9:18
 */
public class TwobaiClient {

    private static final Logger log = LoggerFactory.getLogger(TwobaiClient.class);
    private static final HttpHeaders defaultHttpHeaders;

    static {
        defaultHttpHeaders = new DefaultHttpHeaders();

        //region DEFAULT
        defaultHttpHeaders.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        defaultHttpHeaders.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP_DEFLATE);
        defaultHttpHeaders.set(HttpHeaderNames.USER_AGENT, "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html）");
        defaultHttpHeaders.set("DNT", 1);
        defaultHttpHeaders.set(HttpHeaderNames.CACHE_CONTROL, "max-age=0");
        //endregion
    }

    private TwobaiClientPool twobaiClientPool;

    public TwobaiClient(TwobaiClientPool twobaiClientPool) {
        this.twobaiClientPool = twobaiClientPool;
    }

    public HttpReceive request(HttpSend httpSend, long l, TimeUnit timeUnit) {
        HttpReceive httpReceive = new HttpReceive();
        try {
            Channel channel = this.twobaiClientPool.getBootstrap().connect().channel();
            channel.pipeline().addLast(new HttpClientHandler(httpSend, httpReceive));
            channel.pipeline().writeAndFlush(convertRequest(httpSend));

            channel.closeFuture().await(l, timeUnit);

            if (!httpReceive.getIsDone()) {
                httpReceive.setHaveError(true);
                httpReceive.setErrMsg("请求已经超时");
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            httpReceive.setHaveError(true)
                    .setErrMsg(e.getMessage());
        }

        return httpReceive;
    }

    public HttpReceive request(HttpSend httpSend) {
        return request(httpSend, Long.MAX_VALUE, TimeUnit.DAYS);
    }

    private FullHttpRequest convertRequest(HttpSend httpSend) {
        HttpMethod httpMethod;
        Tool_HTTP_METHOD method = httpSend.getMethod();
        Map<String, Object> params = httpSend.getParams();
        Map<String, String> cookies = httpSend.getCookies();
        Map<String, Object> headers = httpSend.getHeaders();

        switch (method) {
            case GET:
                httpMethod = HttpMethod.GET;
                break;
            case POST:
                httpMethod = HttpMethod.POST;
                break;
            case PUT:
                httpMethod = HttpMethod.PUT;
                break;
            case DELETE:
                httpMethod = HttpMethod.DELETE;
                break;
            case TRACE:
                httpMethod = HttpMethod.TRACE;
                break;
            default:
                httpMethod = HttpMethod.POST;
                break;
        }
        URI uri = twobaiClientPool.getUri();//这里的uri没有path访问路径,只有host和port

        if (httpSend.getUrl().trim().charAt(0) != '/') {
            httpSend.setUrl("/" + httpSend.getUrl());
        }

        String uriScheme = uri.getScheme();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(uriScheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(uriScheme)) {
                port = 443;
            }
        }

        QueryStringEncoder queryStringEncoder = new QueryStringEncoder(uriScheme + "://" + uri.getHost() + ":" + port + httpSend.getUrl(), httpSend.getCharset());

        if (params != null) {
            params.forEach((key, value) -> {
                queryStringEncoder.addParam(key, value.toString());
            });
        }

        URI sendURI;
        try {
            sendURI = new URI(queryStringEncoder.toString());
        } catch (URISyntaxException e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod, sendURI.toString());

        if (cookies != null) {
            List<Cookie> cookieList = Lists.newArrayListWithCapacity(cookies.size());
            cookies.forEach((key, value) -> {
                cookieList.add(new DefaultCookie(key, value));
            });

            request.headers().set(
                    HttpHeaderNames.COOKIE,
                    ClientCookieEncoder.STRICT.encode(cookieList)
            );
        }

        request.headers().add(defaultHttpHeaders);
        if (headers != null) {
            headers.forEach((key, value) -> {
                request.headers().set(key, value);
            });
        }

        request.headers().set(HttpHeaderNames.HOST, uri.getHost() + ":" + port);
        request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED + ";charset" + httpSend.getCharset().toString());
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        return request;
    }

}
