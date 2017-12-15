package pro.tools.http.okhttp;

import com.google.common.collect.Maps;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.data.text.ToolJson;
import pro.tools.format.ToolFormat;
import pro.tools.http.pojo.HttpMethod;
import pro.tools.http.pojo.HttpReceive;
import pro.tools.http.pojo.HttpSend;

import java.util.AbstractCollection;
import java.util.Map;
import java.util.Set;

/**
 * Created on 17/9/3 13:57 星期日.
 *
 * @author sd
 */
public class ToolSendHttp {

    private static final Logger log = LoggerFactory.getLogger(ToolSendHttp.class);

    public static HttpReceive get(String url) {
        return send(HttpSend.of(url).setMethod(HttpMethod.GET));
    }

    public static HttpReceive post(String url) {
        return send(HttpSend.of(url).setMethod(HttpMethod.POST));
    }

    public static HttpReceive post(String url, Map<String, Object> params) {
        return send(HttpSend.of(url, params).setMethod(HttpMethod.POST));
    }

    public static HttpReceive send(HttpSend httpSend) {
        return send(httpSend, ToolHttpBuilder.getDefaultClient());
    }

    public static HttpReceive send(HttpSend httpSend, OkHttpClient.Builder okHttpClientBuilder) {
        return send(httpSend, okHttpClientBuilder.build());
    }

    public static HttpReceive send(HttpSend httpSend, OkHttpClient okHttpClient) {
        final HttpReceive httpReceive = new HttpReceive();
        httpReceive.setHaveError(true);
        try {
            Request request = convertRequest(httpSend);
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            ResponseBody body = response.body();

            if (body == null) {
                throw new NullPointerException("response.body is null");
            }

            final Map<String, String> responseHeaders = Maps.newHashMap();
            if (httpSend.getNeedReceiveHeaders()) {
                final Headers headers = response.headers();
                final Set<String> headerNameSet = headers.names();
                headerNameSet.forEach(oneHeaderName -> {
                    final String oneHeaderValue = headers.get(oneHeaderName);
                    responseHeaders.put(oneHeaderName, oneHeaderValue);
                });
            }

            int responseStatusCode = response.code();
            if (responseStatusCode != 200) {
                httpReceive.setErrMsg("本次请求响应码不是200，是" + responseStatusCode)
                ;
            } else {
                String responseBody = body.string();
                if (log.isDebugEnabled()) {
                    log.debug(responseBody);
                }
                httpReceive.setResponseBody(responseBody)
                        .setHaveError(false)
                        .setStatusCode(responseStatusCode)
                        .setStatusText(responseStatusCode + "")
                        .setResponseHeader(responseHeaders)
                ;
            }

            response.close();
            okHttpClient.dispatcher().executorService().shutdown();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(ToolFormat.toException(e), e);
            }
            httpReceive.setErrMsg(e.getMessage())
                    .setThrowable(e)
                    .setIsDone(true)
            ;
        }

        httpReceive.setIsDone(true);
        return httpReceive;
    }

    private static Request convertRequest(HttpSend httpSend) {
        final Request.Builder requestBuilder = new Request.Builder();
        final FormBody.Builder formBodyBuilder = new FormBody.Builder();

        final Map<String, Object> params = httpSend.getParams();
        final String url = httpSend.getUrl();
        final Map<String, Object> headers = httpSend.getHeaders();

        if (params != null) {
            params.forEach((key, value) -> {
                String v;
                if (value instanceof AbstractCollection
                        || value instanceof Map
                        || value instanceof Number
                        || value instanceof String) {
                    v = value.toString();
                } else {
                    v = ToolJson.anyToJson(value);
                }
                formBodyBuilder.add(key, v);
            });
        }

        if (headers != null) {
            headers.forEach((key, value) -> {
                if (value instanceof AbstractCollection
                        || value instanceof Map
                        || value instanceof Number
                        || value instanceof String) {
                    requestBuilder.addHeader(key, value.toString());
                } else {
                    requestBuilder.addHeader(key, ToolJson.anyToJson(value));
                }
            });
        }

        final FormBody requestBody = formBodyBuilder.build();

        if (httpSend.getMethod() == HttpMethod.GET) {
            return requestBuilder.url(url).build();
        } else {
            return requestBuilder.url(url).method(httpSend.getMethod().name(), requestBody).build();
        }
    }
}
