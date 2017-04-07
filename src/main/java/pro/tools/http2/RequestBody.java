package pro.tools.http2;

import com.ning.http.client.cookie.Cookie;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class RequestBody {
    private String url;
    private Map<String, Object> param;
    private boolean needMsg = true;
    private boolean needErrMsg = true;
    private List<Cookie> cookieList;
    private Map<String, Object> headers;
    private HTTP_METHOD method;
    private long timeout = 10;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public RequestBody() {
    }

    public RequestBody(String url, Map<String, Object> param, HTTP_METHOD method) {
        this.url = url;
        this.param = param;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public RequestBody setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public RequestBody setParam(Map<String, Object> param) {
        this.param = param;
        return this;
    }

    public boolean isNeedMsg() {
        return needMsg;
    }

    public RequestBody setNeedMsg(boolean needMsg) {
        this.needMsg = needMsg;
        return this;
    }

    public boolean isNeedErrMsg() {
        return needErrMsg;
    }

    public RequestBody setNeedErrMsg(boolean needErrMsg) {
        this.needErrMsg = needErrMsg;
        return this;
    }

    public List<Cookie> getCookieList() {
        return cookieList;
    }

    public RequestBody setCookieList(List<Cookie> cookieList) {
        this.cookieList = cookieList;
        return this;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public RequestBody setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public HTTP_METHOD getMethod() {
        return method;
    }

    public RequestBody setMethod(HTTP_METHOD method) {
        this.method = method;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    public RequestBody setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public RequestBody setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }
}