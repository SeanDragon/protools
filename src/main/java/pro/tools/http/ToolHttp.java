package pro.tools.http;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;
import pro.tools.data.text.ToolRegex;
import pro.tools.format.ToolFormat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
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

    public static ToolHttpReceive sendGet(String url) {
        ToolHttpSend send = new ToolHttpSend(url)
                .setMethod(Tool_HTTP_METHOD.GET);
        return sendHttp(send);
    }

    public static ToolHttpReceive sendPost(String url) {
        ToolHttpSend send = new ToolHttpSend(url)
                .setMethod(Tool_HTTP_METHOD.POST);
        return sendHttp(send);
    }

    public static ToolHttpReceive sendGet(String url, Map<String, Object> param) {
        ToolHttpSend send = new ToolHttpSend(url, param, Tool_HTTP_METHOD.GET);
        return sendHttp(send);
    }

    public static ToolHttpReceive sendPost(String url, Map<String, Object> param) {
        ToolHttpSend send = new ToolHttpSend(url, param, Tool_HTTP_METHOD.POST);
        return sendHttp(send);
    }

    /**
     * 用于请求http
     *
     * @param send 里面包含请求的信息
     * @return 响应的信息
     */
    public static ToolHttpReceive sendHttp(ToolHttpSend send) {

        ToolHttpReceive toolHttpReceive = new ToolHttpReceive();
        toolHttpReceive.setHaveError(true);

        String url = send.getUrl();
        if (!ToolRegex.isURL(url)) {
            if (send.isNeedErrMsg()) {
                toolHttpReceive.setErrMsg("不是一个有效的URL");
            }
            return toolHttpReceive;
        }

        Map<String, Object> param = send.getParam();
        List<Cookie> cookies = send.getCookieList();
        Map<String, Object> headers = send.getHeaders();
        Tool_HTTP_METHOD method = send.getMethod();
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

        if (cookies != null) {
            cookies.forEach(requestBuilder::addCookie);
        }

        ListenableFuture<Response> future = requestBuilder.execute();

        try {
            Response response = future.get();
            toolHttpReceive.setResponseCookieList(response.getCookies());
            toolHttpReceive.setStatusCode(response.getStatusCode());
            toolHttpReceive.setStatusText(response.getStatusText());
            if (send.isNeedMsg()) {
                String responseBody = response.getResponseBody();
                toolHttpReceive.setResponseBody(responseBody);
            }

            //将原生对象放入,方便复杂操作
            toolHttpReceive.setResponse(response);
            //将是否有错误信息设置为无
            toolHttpReceive.setHaveError(false);

        } catch (InterruptedException e) {
            toolHttpReceive.setErrMsg("http组件出现问题!\n" + ToolFormat.toException(e));
        } catch (IOException e) {
            toolHttpReceive.setErrMsg("获取返回内容失败!\n" + ToolFormat.toException(e));
        } catch (ExecutionException e) {
            toolHttpReceive.setErrMsg("访问URL失败!\n" + ToolFormat.toException(e));
        }

        if (!send.isNeedErrMsg()) {
            toolHttpReceive.setErrMsg("");
        }

        return toolHttpReceive;
    }

    public static HttpBuilder getBuilder() {
        return builder;
    }

    public static void setBuilder(HttpBuilder builder) {
        ToolHttp.builder = builder;
    }
}