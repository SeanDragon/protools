package pro.tools.http.twobai.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-19 14:51
 */
public class ReadHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    //@Override
    //public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    //    System.out.println("ReadHandler.channelRead");
    //    System.out.println(msg.getClass());
    //    System.out.println(msg);
    //    super.channelRead(ctx, msg);
    //}
}
