package pro.tools.http.netty.pojo;

import com.google.common.base.MoreObjects;
import pro.tools.constant.StrConst;
import pro.tools.http.jdk.Tool_HTTP_METHOD;

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
    private Map<String, String> cookies;//cookie列表
    private Map<String, Object> headers;//header列表
    private Tool_HTTP_METHOD method;     //访问方法
    private Charset charset;//编码

    public HttpSend(String url, Map<String, Object> params) {
        init();
        this.url = url;
        this.params = params;
    }

    public HttpSend(String url, Map<String, Object> params, Tool_HTTP_METHOD method) {
        init();
        this.url = url;
        this.params = params;
        this.method = method;
    }

    /**
     * 初始化
     */
    private void init() {
        method = Tool_HTTP_METHOD.GET;
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

    public Map<String, String> getCookies() {
        return cookies;
    }

    public HttpSend setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public HttpSend setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public Tool_HTTP_METHOD getMethod() {
        return method;
    }

    public HttpSend setMethod(Tool_HTTP_METHOD method) {
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
                .add("cookies", cookies)
                .add("headers", headers)
                .add("method", method)
                .add("charset", charset)
                .toString();
    }
}
