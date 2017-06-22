package pro.tools.http.netty.client;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.http.netty.clientpool.ClientPool;
import pro.tools.http.netty.handler.HandlerInitializer;
import pro.tools.http.netty.http.Request;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**

 */
public class NettyClient extends AbstractClient {

    private static final Logger log = LoggerFactory.getLogger(NettyClient.class);

    private NioEventLoopGroup group;
    private volatile NioSocketChannel channel;
    private AtomicInteger connectNumber = new AtomicInteger(0);

    public NettyClient(ClientPool pool) {
        super(pool);
    }

    /**
     * 处理请求
     *
     * @param request
     *
     * @return
     */
    @Override
    public void request(Request request) {
        if (this.getStatus().equals(ClientStatus.Ready)) {
            this.setStatus(ClientStatus.Working);
        } else {
            log.warn("client状态不对啊");
            return;
        }
        URI uri;
        try {
            uri = new URI(this.getScheme(), null, this.getRemoteHost(), this.getPort(), request.getPath(), null, null);
        } catch (URISyntaxException e) {
            log.error("host没问题,path访问出错", e);
            throw new RuntimeException(e);
        }

        FullHttpRequest fullHttpRequest;

        String rawPath = uri.getRawPath();

        log.warn(String.format("\r\n---------START---------\r\n%s\r\n%s\r\n%s\r\n----------END----------\r\n"
                , "value:\t" + rawPath
                , "key:\trawPath"
                , "site:\tpro.tools.http.netty.client.NettyClient.request()")
        );

        HttpMethod method;
        switch (request.getMethod()) {
            case GET:
                method = HttpMethod.GET;
                break;
            case POST:
                method = HttpMethod.POST;
                break;
            case PUT:
                method = HttpMethod.PUT;
                break;
            case DELETE:
                method = HttpMethod.DELETE;
                break;
            default:
                method = HttpMethod.POST;
                break;
        }

        fullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, rawPath);

        //如果是post请求,做特殊处理
        if (method.equals(HttpMethod.POST)) {
            fullHttpRequest.content().writeBytes(request.getBody());
            fullHttpRequest.headers().add("content-length", fullHttpRequest.content().readableBytes());
        }

        for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
            fullHttpRequest.headers().add(header.getKey(), header.getValue());
        }

        fullHttpRequest.headers().add("Host", this.getRemoteHost());
        fullHttpRequest.headers().add("Connection", "keep-alive");
        this.channel.pipeline().write(fullHttpRequest);
        this.channel.pipeline().flush();
    }

    /**
     * 取消正在运行的request
     */
    @Override
    public void cancel() {
        if (this.getStatus().equals(ClientStatus.Working)) {
            log.warn("正在工作的request被取消了");
            this.channel.close();
            this.start();
        }
    }

    /**
     * 当前的连接出了异常
     *
     * @param t
     *
     * @return
     */
    public synchronized void exception(Throwable t) {
        if (this.getStatus().equals(ClientStatus.Stopped)) {
            return;
        }
        log.warn("连接出现了异常,将会进行重连接");
        if (this.getStatus().equals(ClientStatus.Ready)) {
            this.getClientPool().removeClient(this);
        } else if (this.getStatus().equals(ClientStatus.Working)) {
            log.error("request执行时出现异常, request is :" + this.getRequest());
            this.getRequest().getFuture().exception(t);
        }
        this.start();  //重启
    }

    /**
     * 当连接断开了之后调用的方法
     */
    public synchronized void disconnected() {
        if (this.getStatus().equals(ClientStatus.Stopped)) {
            return;
        }
        log.warn("连接断开了，将会尝试重新连接");
        if (this.getStatus().equals(ClientStatus.Ready)) {
            this.getClientPool().removeClient(this);
        } else if (this.getStatus().equals(ClientStatus.Working)) {
            log.error("request执行时出现异常, request is :" + this.getRequest());
            this.getRequest().getFuture().exception(new Exception("连接断开了"));
        }
        this.start();  //重启
    }

    /**
     * 这里主要是创建channel，然后将其注册到group上面去，另外要看连接是否成功
     * 如果连接失败的话要调度重新建立连接，另外还要控制重连接的数量，如果超过十次还不成功那就算了吧
     * 启动组件
     */
    @Override
    public void start() {
        this.setStatus(ClientStatus.Starting);
        if (this.channel != null) {
            this.channel.close();
            this.channel = null;
        }
        this.channel = new NioSocketChannel();
        this.channel.pipeline().addFirst(new HandlerInitializer(this));
        this.group.register(this.channel);
        this.channel.connect(new InetSocketAddress(this.getRemoteHost(), this.getPort())).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.channel().isActive()) {
                connectNumber.set(0);  //连接上了之后就可以刷新成0
                log.info("连接建立成功, host : " + getRemoteHost());
            } else {
                int number = connectNumber.incrementAndGet();
                if (number > 10) {
                    log.error("我擦，尝试这么多次，还是没有连接上，算了吧");
                    getClientPool().stop();
                    return;
                }
                log.warn("连接超时，即将尝试重新连接");
                NettyClient.this.getClientPool().scBuild(NettyClient.this::start, 2000);
            }
        });
    }

    /*
     * 停止组件
     */
    @Override
    public void stop() {
        super.stop();
        this.setStatus(ClientStatus.Stopped);
        this.channel.close();
    }

    public NioSocketChannel getChannel() {
        return channel;
    }

    public void setGroup(NioEventLoopGroup group) {
        this.group = group;
    }
}
