package pro.tools.http.netty.clientpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.constant.HttpConst;
import pro.tools.http.netty.client.Client;
import pro.tools.http.netty.future.Future;
import pro.tools.http.netty.http.Request;
import pro.tools.http.netty.http.RequestFuture;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**

 */
public abstract class AbstractClientPool implements ClientPool {

    private static final Logger log = LoggerFactory.getLogger(AbstractClientPool.class);

    private int size;
    private String scheme;
    private int port;
    private String remoteHost;
    private LinkedBlockingQueue<Client> clients;

    public AbstractClientPool(int size, String remoteHost) {
        this.scheme = HttpConst.HttpScheme.HTTP;
        this.port = 80;
        this.size = size;
        this.remoteHost = remoteHost;
        this.clients = new LinkedBlockingQueue<>(this.size);
    }

    public AbstractClientPool(int size, String remoteHost, int port) {
        this.scheme = HttpConst.HttpScheme.HTTP;
        this.port = port;
        this.size = size;
        this.remoteHost = remoteHost;
        this.clients = new LinkedBlockingQueue<>(this.size);
    }

    public AbstractClientPool(int size, String remoteHost, String scheme) {
        this.scheme = scheme;
        this.port = 80;
        this.size = size;
        this.remoteHost = remoteHost;
        this.clients = new LinkedBlockingQueue<>(this.size);
    }

    public AbstractClientPool(int size, String remoteHost, int port, String scheme) {
        this.scheme = scheme;
        this.port = port;
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

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getScheme() {
        return this.scheme;
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
        log.warn("有client退出了");
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
                client = this.clients.poll(365, TimeUnit.DAYS);
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
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
     * @param timeout
     *
     * @return
     */
    @Override
    public RequestFuture requestWithTimeOut(Request request, int timeout) {
        long before = System.currentTimeMillis();
        final RequestFuture future = new RequestFuture(request);
        Client client;
        while (true) {
            try {
                client = this.clients.poll(timeout, TimeUnit.MILLISECONDS);
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
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

        timeout = (int) (timeout - (after - before));
        this.scBuild(future::timeOut, timeout);
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
