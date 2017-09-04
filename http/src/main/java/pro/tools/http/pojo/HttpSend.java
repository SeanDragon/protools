package pro.tools.http.pojo;

import com.google.common.base.MoreObjects;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import pro.tools.constant.StrConst;

import javax.crypto.spec.OAEPParameterSpec;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 13:50
 */
public class HttpSend implements java.io.Serializable {
    private String url;
    private Map<String, Object> params;//参数
    private Map<String, Object> headers;//header列表
    private HttpMethod method;     //访问方法
    private Charset charset;//编码

    public HttpSend(String url) {
        init();
        this.url = url;
    }

    public HttpSend(String url, Map<String, Object> params) {
        init();
        this.url = url;
        this.params = params;
    }

    public HttpSend(String url, Map<String, Object> params, HttpMethod method) {
        init();
        this.url = url;
        this.params = params;
        this.method = method;
    }

    public static HttpSend of(String url) {
        return new HttpSend(url);
    }

    public static HttpSend of(String url, Map<String, Object> params) {
        return new HttpSend(url, params);
    }

    public static HttpSend of(String url, Map<String, Object> params, HttpMethod method) {
        return new HttpSend(url, params, method);
    }

    /**
     * 初始化
     */
    private void init() {
        method = HttpMethod.GET;
        charset = StrConst.DEFAULT_CHARSET;
    }

    //region GetterSetter


    public String getUrl() {
        return url;
    }

    public HttpSend setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public HttpSend setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public HttpSend setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpSend setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public Charset getCharset() {
        return charset;
    }

    public HttpSend setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }
    //endregion


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .add("params", params)
                .add("headers", headers)
                .add("method", method)
                .add("charset", charset)
                .toString();
    }
}
