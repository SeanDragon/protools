package pro.tools.http.okhttp;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Created on 17/9/3 13:59 星期日.
 *
 * @author sd
 */
public class ToolHttpBuilder {

    /**
     * 读超时 秒
     */
    private final static long DEFAULT_READ_TIMEOUT = 5;
    /**
     * 写超时 秒
     */
    private final static long DEFAULT_WRITE_TIMEOUT = 5;
    /**
     * 连接超时 秒
     */
    private final static long DEFAULT_CONNECT_TIMEOUT = 5;
    /**
     * 长连接时间 分钟
     */
    private final static long DEFAULT_KEEP_ALIVE_DURATION = 1;
    /**
     * 最大空闲连接
     */
    private final static int DEFAULT_MAX_IDLE_CONNECTIONS = 5;

    private static OkHttpClient.Builder defaultBuilder;

    static {
        init();
    }

    private static void init() {
        defaultBuilder = new OkHttpClient.Builder();
        //设置超时时间
        defaultBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        defaultBuilder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        defaultBuilder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        //连接池
        ConnectionPool connectionPool = new ConnectionPool(DEFAULT_MAX_IDLE_CONNECTIONS
                , DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.MINUTES);
        defaultBuilder.connectionPool(connectionPool);

        //失败重连
        defaultBuilder.retryOnConnectionFailure(true);
    }

    public synchronized static void setDefaultBuilder(OkHttpClient.Builder defaultBuilder) {
        ToolHttpBuilder.defaultBuilder = defaultBuilder;
    }

    public static OkHttpClient.Builder getDefaultBuilder() {
        return defaultBuilder;
    }

    public static OkHttpClient buildOkHttpClient() {
        return defaultBuilder.build();
    }
}
