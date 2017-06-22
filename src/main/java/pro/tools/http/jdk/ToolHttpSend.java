package pro.tools.http.jdk;

import com.google.common.base.MoreObjects;
import com.ning.http.client.cookie.Cookie;
import pro.tools.constant.StrConst;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public final class ToolHttpSend {
    private String url;                     //访问的url
    private Map<String, Object> param;      //参数
    private boolean needMsg;                //是否需要返回结果
    private boolean needErrMsg;             //是否需要返回错误信息
    private List<Cookie> cookieList;        //cookie列表
    private Map<String, Object> headers;    //header列表
    private Tool_HTTP_METHOD method;             //访问方法
    private Charset charset;

    public ToolHttpSend(String url) {
        init();
        this.url = url;
    }

    public ToolHttpSend(String url, Map<String, Object> param) {
        init();
        this.url = url;
        this.param = param;
    }

    public ToolHttpSend(String url, Map<String, Object> param, Tool_HTTP_METHOD method) {
        init();
        this.url = url;
        this.param = param;
        this.method = method;
    }

    /**
     * 初始化
     */
    private void init() {
        needMsg = true;
        needErrMsg = true;
        method = Tool_HTTP_METHOD.GET;
        charset = StrConst.DEFAULT_CHARSET;
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

    public Charset getCharset() {
        return charset;
    }

    public ToolHttpSend setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .add("param", param)
                .add("needMsg", needMsg)
                .add("needErrMsg", needErrMsg)
                .add("cookieList", cookieList)
                .add("headers", headers)
                .add("method", method)
                .add("charset", charset)
                .toString();
    }
}