package pro.tools.http.netty.clientpool;

import pro.tools.http.netty.client.Client;
import pro.tools.http.netty.future.Future;
import pro.tools.http.netty.http.Request;
import pro.tools.http.netty.http.RequestFuture;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**

 */
public abstract class AbstractClientPool implements ClientPool {
    private int size;
    private String remoteHost;
    private LinkedBlockingQueue<Client> clients;

    public AbstractClientPool(int size, String remoteHost) {
        this.size = size;
        this.remoteHost = remoteHost;
        this.clients = new LinkedBlockingQueue<>(this.size);
    }

    /**
     * 获取当前连接的远端的地址
     *
     * @return
     */
    @Override
    public String getRemoteHost() {
        return this.remoteHost;
    }

    /**
     * 获取当前pool的规模，其实也就是有多少个client
     *
     * @return
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * 当有client退出的时候的处理，相当于提供给client的回调
     *
     * @param client
     */
    @Override
    public void clientExit(Client client) {
        Logger.getLogger("main").warning("有client退出了");
        this.clients.remove(client);
    }

    /**
     * 发送一个http请求，并返回一个RequestFuture
     *
     * @param request
     *
     * @return
     */
    @Override
    public RequestFuture request(Request request) {

        Client client;
        while (true) {
            try {
                client = this.clients.poll(1000, TimeUnit.DAYS);
                break;
            } catch (InterruptedException ignored) {
            }
        }

        RequestFuture future = new RequestFuture(request);
        request.setFuture(future);
        future.setClient(client);
        client.setRequest(request);
        future.setStatus(Future.FutureStatus.Running);
        client.request(request);

        return future;
    }

    /**
     * 带有超时控制的请求
     *
     * @param request
     * @param time
     *
     * @return
     */
    @Override
    public RequestFuture requestWithTimeOut(Request request, int time) {
        long before = System.currentTimeMillis();
        final RequestFuture future = new RequestFuture(request);
        Client client;
        while (true) {
            try {
                client = this.clients.poll(time, TimeUnit.MILLISECONDS);
                break;
            } catch (InterruptedException ignored) {
            }

        }
        if (client == null) {  //超时了居然都还没有拿到client来处理
            future.timeOut();
            return future;
        }
        long after = System.currentTimeMillis();

        future.setClient(client);
        client.setRequest(request);
        request.setFuture(future);
        future.setStatus(Future.FutureStatus.Running);

        time = (int) (time - (after - before));
        this.scBuild(future::timeOut, time);
        client.request(request);
        return future;


    }

    /**
     * 用于构建具体的client
     *
     * @return
     */
    public abstract Client newClient();

    /**
     * 启动组件
     */
    @Override
    public void start() {
        for (int i = 0; i < this.size; i++) {
            this.newClient().start();  //在client确实启动好了之后会加入到pool里面
        }
    }

    /**
     * 停止组件
     */
    @Override
    public void stop() {
        while (this.clients.size() > 0) {
            Client client = this.clients.peek();
            client.stop();
        }
    }


    /**
     * 向当前的pool添加client
     *
     * @param client
     */
    @Override
    public void addClient(Client client) {
        try {
            this.clients.put(client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 移除一个client
     *
     * @param client
     */
    @Override
    public void removeClient(Client client) {
        this.clients.remove(client);
    }
}
