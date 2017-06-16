package pro.tools.http;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;
import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolRegex;
import pro.tools.format.ToolFormat;

import java.io.IOException;
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
     * @param toolHttpSend 里面包含请求的信息
     * @return 响应的信息
     */
    public static ToolHttpReceive sendHttp(ToolHttpSend toolHttpSend) {

        ToolHttpReceive toolHttpReceive = new ToolHttpReceive();
        toolHttpReceive.setHaveError(true);

        String url = toolHttpSend.getUrl();
        if (!ToolRegex.isURL(url)) {
            if (toolHttpSend.isNeedErrMsg()) {
                toolHttpReceive.setErrMsg("不是一个有效的URL");
            }
            return toolHttpReceive;
        }

        Map<String, Object> param = toolHttpSend.getParam();
        List<Cookie> cookies = toolHttpSend.getCookieList();
        Map<String, Object> headers = toolHttpSend.getHeaders();
        Tool_HTTP_METHOD method = toolHttpSend.getMethod();

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
        requestBuilder.setBodyEncoding(StrConst.DEFAULT_CHARSET_NAME);

        if (param != null) {
            param.forEach((key, value) -> requestBuilder.addQueryParam(key, value.toString()));
        }

        //设置基本请求头
        requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + StrConst.DEFAULT_CHARSET_NAME);

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
            if (toolHttpSend.isNeedMsg()) {
                String responseBody = response.getResponseBody();
                responseBody = new String(responseBody.getBytes(), StrConst.DEFAULT_CHARSET_NAME);
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

        if (!toolHttpSend.isNeedErrMsg()) {
            toolHttpReceive.setErrMsg("");
        }

        return toolHttpReceive;
    }


    public static void setBuilder(HttpBuilder builder) {
        ToolHttp.builder = builder;
    }
}