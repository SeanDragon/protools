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
import pro.tools.http.pojo.HttpException;
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

    public static HttpReceive post(String url, Map<String, Object> params) {
        return send(HttpSend.of(url, params).setMethod(HttpMethod.POST));
    }

    public static HttpReceive send(HttpSend httpSend) {
        return send(httpSend, ToolHttpBuilder.getDefaultBuilder());
    }

    public static HttpReceive send(HttpSend httpSend, OkHttpClient.Builder clientBuilder) {
        HttpReceive httpReceive = new HttpReceive();
        httpReceive.setHaveError(true);
        try {
            OkHttpClient okHttpClient = clientBuilder.build();
            Request request = convertRequest(httpSend);
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            ResponseBody body = response.body();

            if (body == null) {
                throw new NullPointerException("response.body is null");
            }

            Headers headers = response.headers();
            Set<String> headerNameSet = headers.names();
            Map<String, String> responseHeaders = Maps.newHashMap();
            headerNameSet.forEach(oneHeaderName -> {
                String oneHeaderValue = headers.get(oneHeaderName);
                responseHeaders.put(oneHeaderName, oneHeaderValue);
            });

            int responseStatusCode = response.code();
            if (responseStatusCode != 200) {
                httpReceive.setHaveError(true)
                        .setErrMsg("本次请求响应码不是200，是" + responseStatusCode)
                ;
            } else {
                httpReceive.setResponseBody(body.string())
                        .setHaveError(false)
                        .setStatusCode(responseStatusCode)
                        .setStatusText(responseStatusCode + "")
                        .setResponseHeader(responseHeaders)
                ;
            }

            response.close();
            okHttpClient.dispatcher().executorService().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ToolFormat.toException(e), e);
            httpReceive.setErrMsg(ToolFormat.toException(e))
                    .setThrowable(e)
                    .setIsDone(true)
            ;
        }

        httpReceive.setIsDone(true);
        return httpReceive;
    }

    private static Request convertRequest(HttpSend httpSend) throws HttpException {
        Request.Builder requestBuilder = new Request.Builder();
        FormBody.Builder requestBodyBuilder = new FormBody.Builder();

        Map<String, Object> params = httpSend.getParams();
        String url = httpSend.getUrl();
        Map<String, Object> headers = httpSend.getHeaders();

        if (params != null) {
            params.forEach((key, value) -> {
                String v;
                if (value instanceof AbstractCollection
                        || value instanceof Map
                        || value instanceof Number
                        || value instanceof String) {
                    // try {
                    //     v = URLDecoder.decode(value.toString(), StrConst.DEFAULT_CHARSET_NAME);
                    // } catch (UnsupportedEncodingException e) {
                    //     throw new RuntimeException(e);
                    // }
                    v = value.toString();
                } else {
                    v = ToolJson.anyToJson(value);
                }
                requestBodyBuilder.add(key, v);
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

        FormBody requestBody = requestBodyBuilder.build();

        if (httpSend.getMethod() != HttpMethod.GET) {
            return requestBuilder.url(url).method(httpSend.getMethod().name(), requestBody).build();
        } else {
            return requestBuilder.url(url).build();
        }
    }
}
