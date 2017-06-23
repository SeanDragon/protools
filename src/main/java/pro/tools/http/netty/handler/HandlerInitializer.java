package pro.tools.http.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.http.netty.client.NettyClient;
import pro.tools.http.netty.future.Future;
import pro.tools.http.netty.http.Response;

public class HandlerInitializer implements ChannelInboundHandler {

    private static final Logger log = LoggerFactory.getLogger(HandlerInitializer.class);
    private NettyClient client;

    public HandlerInitializer(NettyClient client) {
        this.client = client;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelUnregistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        NioSocketChannel channel = this.client.getChannel();
        channel.pipeline().remove(this);
        channel.pipeline().addFirst(new HttpClientCodec());  //为当前的channel添加
        channel.pipeline().addLast(new ReadHandler());
        channel.pipeline().addLast(this);
        client.ready();
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        client.disconnected();
        channelHandlerContext.disconnect();
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        channelHandlerContext.fireChannelRead(o);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelReadComplete();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        channelHandlerContext.fireUserEventTriggered(o);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        client.exception(throwable);
    }

    private class ReadHandler extends ChannelInboundHandlerAdapter {
        private DefaultHttpResponse response;
        private ByteBuf body;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpResponse) {
                this.response = (DefaultHttpResponse) msg;
            }

            if (msg instanceof HttpContent) {
                HttpContent content = (HttpContent) msg;
                if (this.body == null) {
                    this.body = Unpooled.buffer();
                }
                this.body.writeBytes(content.content());

                if (msg instanceof LastHttpContent) {
                    Response nr = new Response(this.response, this.body);
                    this.body = null;
                    if (client.getRequest().getFuture().getStatus().equals(Future.FutureStatus.Running)) {
                        client.getRequest().getFuture().success(nr);
                    } else {
                        log.warn("请求执行付完毕，但是请求状态不对");
                    }
                    client.ready();
                }
            }
        }
    }
}
