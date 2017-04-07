package pro.tools.http2;

import com.ning.http.client.cookie.Cookie;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class HttpRequest {
    private String url;                 //访问的url
    private Map<String, Object> param;  //参数
    private boolean needMsg = true;     //是否需要返回结果
    private boolean needErrMsg = true;  //是否需要返回错误信息
    private List<Cookie> cookieList;    //cookie列表
    private Map<String, Object> headers;//header列表
    private HTTP_METHOD method;         //访问方法
    private long timeout = 10;          //超时时间
    private TimeUnit timeUnit = TimeUnit.SECONDS;//时间单位

    public HttpRequest() {
    }

    public HttpRequest(String url, Map<String, Object> param, HTTP_METHOD method) {
        this.url = url;
        this.param = param;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public HttpRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public HttpRequest setParam(Map<String, Object> param) {
        this.param = param;
        return this;
    }

    public boolean isNeedMsg() {
        return needMsg;
    }

    public HttpRequest setNeedMsg(boolean needMsg) {
        this.needMsg = needMsg;
        return this;
    }

    public boolean isNeedErrMsg() {
        return needErrMsg;
    }

    public HttpRequest setNeedErrMsg(boolean needErrMsg) {
        this.needErrMsg = needErrMsg;
        return this;
    }

    public List<Cookie> getCookieList() {
        return cookieList;
    }

    public HttpRequest setCookieList(List<Cookie> cookieList) {
        this.cookieList = cookieList;
        return this;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public HttpRequest setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public HTTP_METHOD getMethod() {
        return method;
    }

    public HttpRequest setMethod(HTTP_METHOD method) {
        this.method = method;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    public HttpRequest setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public HttpRequest setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }
}