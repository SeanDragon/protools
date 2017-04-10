package pro.tools.http;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;
import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolRegex;
import pro.tools.tools;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created on 17/4/6 22:06 星期四.
 *
 * @author SeanDragon
 */
public class ToolHttp {

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

        AsyncHttpClientConfig.Builder builder = ToolHttpBuilder.buildDefault();
        if (toolHttpSend.isNeedConnectTimeout()) {
            builder.setConnectTimeout(toolHttpSend.getConnectTimeout());
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
        requestBuilder.setBodyEncoding(StrConst.DEFAULT_CHARSET_NAME);
        if (toolHttpSend.isNeedResponseTimeout()) {
            requestBuilder.setRequestTimeout(toolHttpSend.getResponseTimeout());
        }

        if (param != null) {
            for (Map.Entry<String, Object> oneParam : param.entrySet()) {
                requestBuilder.addQueryParam(oneParam.getKey(), oneParam.getValue().toString());
            }
        }

        //设置基本请求头
        requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + StrConst.DEFAULT_CHARSET_NAME);
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
            if (toolHttpSend.isNeedResponseTimeout()) {
                response = future.get(
                        toolHttpSend.getResponseTimeout(), toolHttpSend.getResponseTimeoutUnit()
                );
            } else {
                response = future.get();
            }
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
            toolHttpReceive.setErrMsg("http组件出现问题!\n" + tools.toException(e));
        } catch (TimeoutException e) {
            toolHttpReceive.setErrMsg("设置超时时间失败!\n" + tools.toException(e));
        } catch (IOException e) {
            toolHttpReceive.setErrMsg("获取返回内容失败!\n" + tools.toException(e));
        } catch (ExecutionException e) {
            toolHttpReceive.setErrMsg("访问URL失败!\n" + tools.toException(e));
        }

        if (!toolHttpSend.isNeedErrMsg()) {
            toolHttpReceive.setErrMsg("");
        }

        return toolHttpReceive;
    }

}