package pro.tools.http.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import pro.tools.http.netty.future.Future;

import java.util.HashMap;

/**

 */
public class Request {
    private Future future;
    private String path;
    private HashMap<String, String> headers = new HashMap<>();
    private RequestMethod method;
    private ByteBuf body;

    public Request(String path, RequestMethod method) {
        this.path = path;
        this.method = method;
        this.body = Unpooled.buffer();
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

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public ByteBuf getBody() {
        return body;
    }

    public void setBody(ByteBuf body) {
        this.body = body;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public enum RequestMethod {
        GET, POST, PUT, DELETE, TRACE
    }
}
