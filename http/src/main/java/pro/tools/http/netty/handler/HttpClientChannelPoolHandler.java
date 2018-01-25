package pro.tools.http.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.AbstractChannelPoolHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-21 13:17
 */
public class HttpClientChannelPoolHandler extends AbstractChannelPoolHandler {


    private final SslContext sslCtx;

    public HttpClientChannelPoolHandler(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void channelCreated(Channel channel) {

        NioSocketChannel nioSocketChannel = (NioSocketChannel) channel;
        nioSocketChannel.config().setTcpNoDelay(true).setKeepAlive(true);

        final ChannelPipeline p = nioSocketChannel.pipeline();

        //HTTPS
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(channel.alloc()));
        }

        p.addLast(new HttpClientCodec(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));

        p.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
    }
}
