package pro.tools.http.jdk;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;

/**
 * http客户端的工厂类
 *
 * @author SeanDragon Create By 2017-04-07 14:59
 */
public final class HttpBuilder {

    /**
     * 异步http客户端的工厂构造对象
     */
    private AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();

    /**
     * 最大请求重连次数
     */
    private int maxRequestRetry = 10;
    /**
     * 最大连接数
     */
    private int maxConnections = 1000;

    /**
     * 连接池开关
     */
    private boolean allowPollingConnections = true;
    /**
     * 重定向追踪开关
     */
    private boolean followRedirect = true;
    /**
     * 压缩请求开关
     */
    private boolean compressionEnforced = true;

    /**
     * 重定向次数限制
     */
    private int maxRedirects = 3;
    /**
     * 连接超时时间
     */
    private int connectTimeout = 200;
    /**
     * 读取请求超时间时间
     */
    private int readTimeout = 200;
    /**
     * 初始化连接池大小
     */
    private int maxConnectionsPerHost = 100;
    /**
     * 用于IO的线程数
     */
    private int ioThreadMultiplier = 10;

    /**
     * UserAgent
     */
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

    AsyncHttpClient buildDefaultClient() {
        builder.setMaxRequestRetry(maxRequestRetry)//尝试重试次数
                .setAllowPoolingConnections(allowPollingConnections)//开启连接池模式
                .setMaxConnections(maxConnections)//最大连接数
                .setConnectTimeout(connectTimeout)//连接超时时间
                .setPooledConnectionIdleTimeout(readTimeout)//设置连接池超时时间
                .setReadTimeout(readTimeout)//读取请求超时时间
                .setFollowRedirect(followRedirect)//开启重定向追踪
                .setMaxRedirects(maxRedirects)//重定向次数限制
                .setUserAgent(userAgent)//设置UserAgent
                .setMaxConnectionsPerHost(maxConnectionsPerHost)//设置初始连接池大小
                .setIOThreadMultiplier(ioThreadMultiplier)//用于IO的线程数
                .setCompressionEnforced(compressionEnforced)//压缩执行
        ;
        return new AsyncHttpClient(builder.build());
    }

    public int getMaxRequestRetry() {
        return maxRequestRetry;
    }

    public HttpBuilder setMaxRequestRetry(int maxRequestRetry) {
        this.maxRequestRetry = maxRequestRetry;
        return this;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public HttpBuilder setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    public boolean isAllowPollingConnections() {
        return allowPollingConnections;
    }

    public HttpBuilder setAllowPollingConnections(boolean allowPollingConnections) {
        this.allowPollingConnections = allowPollingConnections;
        return this;
    }

    public boolean isFollowRedirect() {
        return followRedirect;
    }

    public HttpBuilder setFollowRedirect(boolean followRedirect) {
        this.followRedirect = followRedirect;
        return this;
    }

    public boolean isCompressionEnforced() {
        return compressionEnforced;
    }

    public HttpBuilder setCompressionEnforced(boolean compressionEnforced) {
        this.compressionEnforced = compressionEnforced;
        return this;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public HttpBuilder setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpBuilder setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public HttpBuilder setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public HttpBuilder setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
        return this;
    }

    public int getIoThreadMultiplier() {
        return ioThreadMultiplier;
    }

    public HttpBuilder setIoThreadMultiplier(int ioThreadMultiplier) {
        this.ioThreadMultiplier = ioThreadMultiplier;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public HttpBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }
}
