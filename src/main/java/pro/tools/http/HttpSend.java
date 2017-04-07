package pro.tools.http;

import com.ning.http.client.cookie.Cookie;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class HttpSend {
    private String url;                     //访问的url
    private Map<String, Object> param;      //参数
    private boolean needMsg;                //是否需要返回结果
    private boolean needErrMsg;             //是否需要返回错误信息
    private List<Cookie> cookieList;        //cookie列表
    private Map<String, Object> headers;    //header列表
    private HTTP_METHOD method;             //访问方法
    private int connectTimeout;             //连接超时时间
    private boolean needConnectTimeout;     //是否需要连接超时
    //private TimeUnit connectTimeoutUnit;    //连接超时时间单位
    private int responseTimeout;            //响应超时时间
    private TimeUnit responseTimeoutUnit;   //响应超时时间单位
    private boolean needResponseTimeout;     //是否需要连接超时

    //初始化块
    {
        needMsg = true;
        needErrMsg = true;
        method = HTTP_METHOD.GET;
        connectTimeout = 100;
        //connectTimeoutUnit = TimeUnit.SECONDS;
        responseTimeout = 100;
        responseTimeoutUnit = TimeUnit.SECONDS;
        needConnectTimeout = false;
        needResponseTimeout = false;
    }

    public HttpSend(String url, Map<String, Object> param) {
        this.url = url;
        this.param = param;
    }

    public HttpSend(String url, Map<String, Object> param, HTTP_METHOD method) {
        this.url = url;
        this.param = param;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public HttpSend setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public HttpSend setParam(Map<String, Object> param) {
        this.param = param;
        return this;
    }

    public boolean isNeedMsg() {
        return needMsg;
    }

    public HttpSend setNeedMsg(boolean needMsg) {
        this.needMsg = needMsg;
        return this;
    }

    public boolean isNeedErrMsg() {
        return needErrMsg;
    }

    public HttpSend setNeedErrMsg(boolean needErrMsg) {
        this.needErrMsg = needErrMsg;
        return this;
    }

    public List<Cookie> getCookieList() {
        return cookieList;
    }

    public HttpSend setCookieList(List<Cookie> cookieList) {
        this.cookieList = cookieList;
        return this;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public HttpSend setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public HTTP_METHOD getMethod() {
        return method;
    }

    public HttpSend setMethod(HTTP_METHOD method) {
        this.method = method;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpSend setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    //public TimeUnit getConnectTimeoutUnit() {
    //    return connectTimeoutUnit;
    //}

    //public HttpSend setConnectTimeoutUnit(TimeUnit connectTimeoutUnit) {
    //    this.connectTimeoutUnit = connectTimeoutUnit;
    //    return this;
    //}

    public int getResponseTimeout() {
        return responseTimeout;
    }

    public HttpSend setResponseTimeout(int responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public TimeUnit getResponseTimeoutUnit() {
        return responseTimeoutUnit;
    }

    public HttpSend setResponseTimeoutUnit(TimeUnit responseTimeoutUnit) {
        this.responseTimeoutUnit = responseTimeoutUnit;
        return this;
    }

    public boolean isNeedConnectTimeout() {
        return needConnectTimeout;
    }

    public HttpSend setNeedConnectTimeout(boolean needConnectTimeout) {
        this.needConnectTimeout = needConnectTimeout;
        return this;
    }

    public boolean isNeedResponseTimeout() {
        return needResponseTimeout;
    }

    public HttpSend setNeedResponseTimeout(boolean needResponseTimeout) {
        this.needResponseTimeout = needResponseTimeout;
        return this;
    }
}