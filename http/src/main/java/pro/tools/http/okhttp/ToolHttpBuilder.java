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

    private volatile static OkHttpClient.Builder defaultBuilder;
    private volatile static OkHttpClient defaultClient;
    private volatile static ConnectionPool connectionPool;

    static {
        init();
    }

    public static OkHttpClient.Builder build(long connectTimeout, long readTimeout, long writeTimeout) {
        OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
        newBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectionPool(connectionPool)
                .retryOnConnectionFailure(true)
                .followRedirects(true)
                .followSslRedirects(true);
        return newBuilder;
    }

    private static void init() {
        defaultBuilder = new OkHttpClient.Builder();
        //连接池
        connectionPool = new ConnectionPool(DEFAULT_MAX_IDLE_CONNECTIONS
                , DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.MINUTES);

        //设置超时时间
        defaultBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(connectionPool)
                //失败重连
                .retryOnConnectionFailure(true)
                .followRedirects(true)
                .followSslRedirects(true)
        ;

        defaultClient = defaultBuilder.build();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> connectionPool.evictAll()));
    }

    public synchronized static void setConnectionPool(ConnectionPool connectionPool) {
        ToolHttpBuilder.connectionPool = connectionPool;
    }

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public synchronized static void setDefaultBuilder(OkHttpClient.Builder defaultBuilder) {
        ToolHttpBuilder.defaultBuilder = defaultBuilder;
    }

    public static OkHttpClient.Builder getDefaultBuilder() {
        return defaultBuilder;
    }

    public static OkHttpClient getDefaultClient() {
        return defaultClient;
    }

    public static void setDefaultClient(OkHttpClient defaultClient) {
        ToolHttpBuilder.defaultClient = defaultClient;
    }

    public static OkHttpClient buildOkHttpClient() {
        return defaultBuilder.build();
    }
}
