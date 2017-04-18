package pro.tools.http;

import com.ning.http.client.AsyncHttpClientConfig;

/**
 * http客户端的工厂类
 *
 * @author SeanDragon
 *         Create By 2017-04-07 14:59
 */
public final class ToolHttpBuilder {

    private ToolHttpBuilder() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 异步http客户端的工厂构造对象
     */
    private static AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
    /**
     * 最大请求重连次数
     */
    private static final int MAX_REQUEST_RETRY = 10;
    /**
     * 最大连接数
     */
    private static final int MAX_CONNECTIONS = 100;

    public static AsyncHttpClientConfig.Builder buildDefault() {
        builder.setMaxRequestRetry(MAX_REQUEST_RETRY);
        builder.setMaxConnections(MAX_CONNECTIONS);
        return builder;
    }

    public static AsyncHttpClientConfig.Builder build(int MAX_REQUEST_RETRY, int MAX_CONNECTIONS) {
        builder.setMaxRequestRetry(MAX_REQUEST_RETRY);
        builder.setMaxConnections(MAX_CONNECTIONS);
        return builder;
    }
}
