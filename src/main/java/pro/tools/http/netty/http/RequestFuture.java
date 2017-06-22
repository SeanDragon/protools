package pro.tools.http.netty.http;

import pro.tools.http.netty.client.Client;
import pro.tools.http.netty.future.AbstractFuture;

import java.util.concurrent.TimeoutException;

/**

 */
public class RequestFuture extends AbstractFuture {
    private Request request;
    private Client client;
    private volatile Response response;
    private volatile Throwable throwable;

    public RequestFuture(Request request) {
        this.request = request;
    }

    /**
     * 设置当前的response，相当于做一层记录
     *
     * @param response
     */
    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     * 添加回调，用于处理结果或者异常啥的
     *
     * @param listener
     */
    @Override
    public void addListener(Listener listener) {
        if (this.getStatus().equals(FutureStatus.Success)) {
            for (Listener lis : this.getListeners()) {
                lis.complete(this.response);
            }
        } else {
            super.addListener(listener);
        }
    }

    /**
     * 取消当前任务的执行
     */
    @Override
    public synchronized void cancel() {
        if (this.getStatus().equals(FutureStatus.Pendding) || this.getStatus().equals(FutureStatus.Running)) {
            super.cancel();
            if (this.getStatus().equals(FutureStatus.Running)) {
                this.client.cancel();
            }
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }


    /**
     * 当处理成功之后会调用的方法
     *
     * @param arg
     */
    @Override
    public synchronized void success(Object arg) {
        super.success(arg);
        this.setResponse((Response) arg);
        this.notifyAll();
    }

    /**
     * 当超时的时候会调用的方法
     */
    @Override
    public synchronized void timeOut() {
        super.timeOut();
        this.throwable = new TimeoutException("超时了");
        this.notifyAll();
    }

    /**
     * 当执行过程中出现异常调用的方法
     *
     * @param t
     */
    @Override
    public synchronized void exception(Throwable t) {
        super.exception(t);
        this.throwable = t;
        this.notifyAll();
    }

    public synchronized Response sync() throws Throwable {
        while (true) {
            if (this.response != null) {
                return response;
            }
            if (this.throwable != null) {
                throw throwable;
            }
            try {
                this.wait();
            } catch (InterruptedException ignored) {
            }
        }
    }


}
