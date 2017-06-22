package pro.tools.http.netty.clientpool;

import io.netty.channel.nio.NioEventLoopGroup;
import pro.tools.http.netty.client.Client;
import pro.tools.http.netty.client.NettyClient;

import java.util.concurrent.TimeUnit;

/**

 */
public class NettyClientPool extends AbstractClientPool {
    private NioEventLoopGroup nioEventLoopGroup;

    public NettyClientPool(int size, String remoteHost) {
        super(size, remoteHost);
        this.nioEventLoopGroup = new NioEventLoopGroup(2);
    }

    /**
     * 用于构建具体的client
     *
     * @return
     */
    @Override
    public Client newClient() {
        NettyClient client = new NettyClient(this);
        client.setGroup(this.nioEventLoopGroup);
        return client;
    }

    /**
     * 延迟执行一个task，单位微秒
     *
     * @param task
     * @param time
     */
    @Override
    public void scBuild(Runnable task, int time) {
        this.nioEventLoopGroup.schedule(task, time, TimeUnit.MILLISECONDS);
    }

    public NioEventLoopGroup getNioEventLoopGroup() {
        return nioEventLoopGroup;
    }

    /**
     * 停止组件
     */
    @Override
    public void stop() {
        super.stop();
        this.nioEventLoopGroup.shutdownGracefully();
    }
}
