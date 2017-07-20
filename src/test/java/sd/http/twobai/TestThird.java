package sd.http.twobai;

import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import junit.framework.TestCase;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 19:13
 */
public class TestThird extends TestCase {
    //public void test1() {
    //    EventLoopGroup eventExecutors = new NioEventLoopGroup();
    //
    //    Bootstrap bootstrap = new Bootstrap();
    //    bootstrap.remoteAddress("192.168.15.88", 92)
    //            .handler(new HttpClientInitializer(null));
    //
    //    ChannelPool channelPool = new FixedChannelPool(bootstrap, new ChannelPoolHandler1(), 10);
    //    Channel channel = channelPool.acquire().syncUninterruptibly().getNow();
    //    channel.pipeline().addLast(new HttpClientHandler());
    //    channel.pipeline().writeAndFlush();
    //
    //
    //
    //    channelPool.release(channel).syncUninterruptibly();
    //}
}


class ChannelPoolHandler1 implements ChannelPoolHandler {

    @Override
    public void channelReleased(Channel channel) throws Exception {

    }

    @Override
    public void channelAcquired(Channel channel) throws Exception {

    }

    @Override
    public void channelCreated(Channel channel) throws Exception {

    }
}