package pro.tools.http.netty.http;

import pro.tools.constant.HttpConst;
import pro.tools.http.netty.future.Future;

import java.util.Map;

/**

 */
public class Request {
    private Future future;
    private String path;
    private HttpConst.RequestMethod method;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Map<String, String> cookies;

    public Request(String path) {
        this.path = path;
        this.method = HttpConst.RequestMethod.POST;
    }

    public Request(String path, HttpConst.RequestMethod method) {
        this.path = path;
        this.method = method;
    }

    public Request(String path, Map<String, String> params) {
        this.path = path;
        this.method = HttpConst.RequestMethod.POST;
        this.params = params;
    }

    public Request(String path, HttpConst.RequestMethod method, Map<String, String> params) {
        this.path = path;
        this.method = method;
        this.params = params;
    }

    public Future getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpConst.RequestMethod getMethod() {
        return method;
    }

    public void setMethod(HttpConst.RequestMethod method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Request setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Request setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Request setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }
}
