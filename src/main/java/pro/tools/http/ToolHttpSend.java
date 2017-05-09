package pro.tools.http;

import com.ning.http.client.cookie.Cookie;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class ToolHttpSend {
    private String url;                     //访问的url
    private Map<String, Object> param;      //参数
    private boolean needMsg;                //是否需要返回结果
    private boolean needErrMsg;             //是否需要返回错误信息
    private List<Cookie> cookieList;        //cookie列表
    private Map<String, Object> headers;    //header列表
    private Tool_HTTP_METHOD method;             //访问方法
    private int connectTimeout;             //连接超时时间
    private boolean needConnectTimeout;     //是否需要连接超时
    //private TimeUnit connectTimeoutUnit;  //连接超时时间单位
    private int responseTimeout;            //响应超时时间
    private TimeUnit responseTimeoutUnit;   //响应超时时间单位
    private boolean needResponseTimeout;    //是否需要连接超时


    public static boolean needMsg_default = true;
    public static boolean needErrMsg_default = true;
    public static boolean needConnectTimeout_default = true;
    public static boolean needResponseTimeout_default = true;
    public static Tool_HTTP_METHOD method_default = Tool_HTTP_METHOD.GET;
    public static int connectTimeout_default = 100;
    public static int responseTimeout_default = 100;
    public static TimeUnit responseTimeoutUnit_default = TimeUnit.SECONDS;

    {
        init();
    }

    /**
     * 初始化块
     */
    public void init() {
        needMsg = needMsg_default;
        needErrMsg = needErrMsg_default;
        method = method_default;
        connectTimeout = connectTimeout_default;
        //connectTimeoutUnit = TimeUnit.SECONDS;
        responseTimeout = responseTimeout_default;
        responseTimeoutUnit = responseTimeoutUnit_default;
        needConnectTimeout = needConnectTimeout_default;
        needResponseTimeout = needResponseTimeout_default;
    }

    public ToolHttpSend(String url, Map<String, Object> param) {
        this.url = url;
        this.param = param;
    }

    public ToolHttpSend(String url, Map<String, Object> param, Tool_HTTP_METHOD method) {
        this.url = url;
        this.param = param;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public ToolHttpSend setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public ToolHttpSend setParam(Map<String, Object> param) {
        this.param = param;
        return this;
    }

    public boolean isNeedMsg() {
        return needMsg;
    }

    public ToolHttpSend setNeedMsg(boolean needMsg) {
        this.needMsg = needMsg;
        return this;
    }

    public boolean isNeedErrMsg() {
        return needErrMsg;
    }

    public ToolHttpSend setNeedErrMsg(boolean needErrMsg) {
        this.needErrMsg = needErrMsg;
        return this;
    }

    public List<Cookie> getCookieList() {
        return cookieList;
    }

    public ToolHttpSend setCookieList(List<Cookie> cookieList) {
        this.cookieList = cookieList;
        return this;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public ToolHttpSend setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public Tool_HTTP_METHOD getMethod() {
        return method;
    }

    public ToolHttpSend setMethod(Tool_HTTP_METHOD method) {
        this.method = method;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public ToolHttpSend setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    //public TimeUnit getConnectTimeoutUnit() {
    //    return connectTimeoutUnit;
    //}

    //public ToolHttpSend setConnectTimeoutUnit(TimeUnit connectTimeoutUnit) {
    //    this.connectTimeoutUnit = connectTimeoutUnit;
    //    return this;
    //}

    public int getResponseTimeout() {
        return responseTimeout;
    }

    public ToolHttpSend setResponseTimeout(int responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public TimeUnit getResponseTimeoutUnit() {
        return responseTimeoutUnit;
    }

    public ToolHttpSend setResponseTimeoutUnit(TimeUnit responseTimeoutUnit) {
        this.responseTimeoutUnit = responseTimeoutUnit;
        return this;
    }

    public boolean isNeedConnectTimeout() {
        return needConnectTimeout;
    }

    public ToolHttpSend setNeedConnectTimeout(boolean needConnectTimeout) {
        this.needConnectTimeout = needConnectTimeout;
        return this;
    }

    public boolean isNeedResponseTimeout() {
        return needResponseTimeout;
    }

    public ToolHttpSend setNeedResponseTimeout(boolean needResponseTimeout) {
        this.needResponseTimeout = needResponseTimeout;
        return this;
    }
}