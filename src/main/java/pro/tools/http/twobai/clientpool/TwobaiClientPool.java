package pro.tools.http.twobai.clientpool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import pro.tools.http.twobai.client.TwobaiClient;
import pro.tools.http.twobai.exception.HttpException;
import pro.tools.http.twobai.handler.HttpClientInitializer;
import pro.tools.http.twobai.listener.DisconnectListener;
import pro.tools.http.twobai.pojo.HttpReceive;
import pro.tools.http.twobai.pojo.HttpSend;

import javax.net.ssl.SSLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 9:17
 */
public class TwobaiClientPool {
    private static final EventLoopGroup group = new NioEventLoopGroup();
    private SslContext sslCtx;
    private Bootstrap bootstrap;
    private URI uri;
    private String host;
    private Integer port;
    private DisconnectListener disconnectListener;
    private ArrayBlockingQueue<TwobaiClient> queue;

    public TwobaiClientPool(String URL) throws HttpException {
        try {
            init(URL, 10, null);
        } catch (URISyntaxException | SSLException e) {
            throw new HttpException(e);
        }
    }

    public TwobaiClientPool(String URL, int poolSize) throws HttpException {
        try {
            init(URL, poolSize, null);
        } catch (URISyntaxException | SSLException e) {
            throw new HttpException(e);
        }
    }

    public TwobaiClientPool(String URL, int poolSize, DisconnectListener disconnectListener) throws HttpException {
        try {
            init(URL, poolSize, disconnectListener);
        } catch (URISyntaxException | SSLException e) {
            throw new HttpException(e);
        }
    }

    private void init(String URL, int poolSize, DisconnectListener disconnectListener) throws URISyntaxException, SSLException {
        URI uri = new URI(URL);
        if (uri.getScheme() == null || uri.getHost() == null) {
            throw new IllegalArgumentException("uri不合法");
        }
        String scheme = uri.getScheme();
        String host = uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            System.err.println("仅有HTTP(S)是支持的.");
            return;
        }

        final boolean ssl = "https".equalsIgnoreCase(scheme);

        this.setSSlContext(ssl);

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new HttpClientInitializer(sslCtx))
                .remoteAddress(host, port)
        ;
        this.bootstrap = b;
        this.host = host;
        this.port = port;
        this.uri = uri;
        this.initPool(poolSize);
        if (disconnectListener != null) {
            this.disconnectListener = disconnectListener;
        }
    }

    private void setSSlContext(boolean ssl) throws SSLException {
        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }
    }

    private void initPool(int size) {
        queue = new ArrayBlockingQueue<>(size, true);
        for (int i = 0; i++ < size; ) {
            addClient();
        }
    }

    private void addClient() {
        queue.add(new TwobaiClient(this));
    }

    public TwobaiClient getOneClient() {
        TwobaiClient oneClient = null;
        try {
            oneClient = queue.peek();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //this.addClient();
        return oneClient;
    }

    public HttpReceive request(HttpSend httpSend, long l, TimeUnit timeUnit) {
        TwobaiClient oneClient = getOneClient();
        HttpReceive httpReceive = oneClient.request(httpSend, l, timeUnit);
        if (httpReceive.getThrowable() != null) {
            queue.remove(oneClient);
        }

        if (queue.size() == 0) {
            disconnectListener.doSomething();
        }
        return httpReceive;
    }

    public HttpReceive request(HttpSend httpSend) {
        TwobaiClient oneClient = getOneClient();
        HttpReceive httpReceive = oneClient.request(httpSend);
        if (httpReceive.getThrowable() != null) {
            queue.remove();
        }

        if (queue.size() == 0) {
            disconnectListener.doSomething();
        }
        return httpReceive;
    }

    public void stop() {
        for (int i = 0; i < queue.size(); i++) {
            queue.remove();
        }
        group.shutdownGracefully();
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public URI getUri() {
        return uri;
    }
}
