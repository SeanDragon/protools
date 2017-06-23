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
import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolConvert;
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


        String rawPath = uri.getRawPath();

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

        FullHttpRequest fullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, rawPath);


        Map<String, String> params = request.getParams();
        if (params != null && params.size() > 0) {
            String paramsStr = ToolConvert.map2str(params, "=", "&");
            fullHttpRequest.content().writeBytes(paramsStr.getBytes());
        }


        Map<String, String> headers = request.getHeaders();
        if (headers != null && headers.size() > 0) {
            request.getHeaders().forEach((key, value) -> {
                fullHttpRequest.headers().add(key, value);
            });
        }

        Map<String, String> cookies = request.getCookies();
        if (headers != null && headers.size() > 0) {
            String cookieStr = ToolConvert.map2str(cookies, "=", ";");
            fullHttpRequest.headers().add("Cookie", cookieStr);
        }

        fullHttpRequest.headers().add("DNT", 1);
        fullHttpRequest.headers().add("Content-Length", fullHttpRequest.content().readableBytes());
        fullHttpRequest.headers().add("Accept-Encoding", "gzip, deflate");
        fullHttpRequest.headers().add("Cache-Control", "max-age=0");
        fullHttpRequest.headers().add("User-Agent", "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html）");
        fullHttpRequest.headers().add("Host", this.getRemoteHost() + ":" + this.getPort());
        fullHttpRequest.headers().add("Connection", "keep-alive");
        fullHttpRequest.headers().add("Content-Type", "application/x-www-form-urlencoded;charset=" + StrConst.DEFAULT_CHARSET_NAME);

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
                log.info("连接建立成功, host : " + getRemoteHost() + "\t,port : " + getPort());
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
