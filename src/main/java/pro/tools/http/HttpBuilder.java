package pro.tools.http;

import com.ning.http.client.AsyncHttpClientConfig;

/**
 * http客户端的工厂类
 *
 * @author SeanDragon
 *         Create By 2017-04-07 14:59
 */
class HttpBuilder {
    private static AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
    private static final int MAX_REQUEST_RETRY = 10;
    private static final int MAX_CONNECTIONS = 100;

    static AsyncHttpClientConfig.Builder buildDefault() {
        builder.setMaxRequestRetry(MAX_REQUEST_RETRY);
        builder.setMaxConnections(MAX_CONNECTIONS);
        return builder;
    }
}
