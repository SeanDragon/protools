package pro.tools.http.okhttp;

import okhttp3.*;
import pro.tools.data.text.ToolJson;
import pro.tools.http.pojo.HttpDefaultHeaders;
import pro.tools.http.pojo.HttpMethod;
import pro.tools.http.pojo.HttpSend;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.Map;

/**
 * Created on 17/9/3 13:57 星期日.
 *
 * @author sd
 */
public class ToolSendHttp {

    public static void get(HttpSend httpSend) {
        OkHttpClient okHttpClient = ToolHttpBuilder.buildOkHttpClient();
        Request request = convertRequest(httpSend);
        Call call = okHttpClient.newCall(request);
        Response response;
        try {
            response = call.execute();
            ResponseBody body = response.body();
            System.out.println(body.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Request convertRequest(HttpSend httpSend) {
        Request.Builder requestBuilder = new Request.Builder();
        FormBody.Builder requestBodyBuilder = new FormBody.Builder();

        Map<String, Object> params = httpSend.getParams();

        if (params != null) {
            params.forEach((key, value) -> {
                if (value instanceof AbstractCollection
                        || value instanceof Map
                        || value instanceof Number
                        || value instanceof String) {
                    requestBodyBuilder.addEncoded(key, value.toString());
                } else {
                    requestBodyBuilder.addEncoded(key, ToolJson.anyToJson(value));
                }
            });
        }
        System.out.println(httpSend.getMethod().toString());

        FormBody build = requestBodyBuilder.build();

        if (httpSend.getMethod() != HttpMethod.GET) {
            requestBuilder.method(httpSend.getMethod().name(), build);
        } else {
            requestBuilder.method("POST", build);
        }

        HttpDefaultHeaders.getDefaultHeaders().forEach((key, value) -> {
            if (value instanceof AbstractCollection
                    || value instanceof Map
                    || value instanceof Number
                    || value instanceof String) {
                requestBuilder.addHeader(key, value.toString());
            } else {
                requestBuilder.addHeader(key, ToolJson.anyToJson(value));
            }
        });
        requestBuilder.addHeader("host", httpSend.getUrl());
        requestBuilder.addHeader("content-type", "application/x-www-form-urlencoded;charset" + httpSend.getCharset().toString());

        return requestBuilder
                .url(httpSend.getUrl())
                .build();
    }
}
