package pro.tools.http.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.AbstractChannelPoolHandler;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-21 13:17
 */
public class HttpClientChannelPoolHandler extends AbstractChannelPoolHandler {
    @Override
    public void channelCreated(Channel channel) {
        final ChannelPipeline p = channel.pipeline();

        p.addLast(new HttpClientCodec());

        p.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
    }
}
