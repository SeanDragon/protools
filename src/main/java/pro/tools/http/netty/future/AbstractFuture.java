package pro.tools.http.netty.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReentrantLock;

/**

 */
public abstract class AbstractFuture implements Future {

    private static final Logger log = LoggerFactory.getLogger(AbstractFuture.class);

    //他主要是用来隔离用户的回调，尽量不要在netty的线程中执行用户的回调
    private static ExecutorService service = Executors.newFixedThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });
    //因为这里添加listener与调用listener在不同的线程，所以用这个来保证内存的可见性
    private ReentrantLock tlock = new ReentrantLock();
    private List<Listener> listeners = new ArrayList<Listener>(2);
    //最开始的时候future的状态肯定是pendding
    private volatile FutureStatus status = FutureStatus.Pendding;

    /**
     * 添加回调，用于处理结果或者异常啥的
     *
     * @param listener
     */
    @Override
    public void addListener(Listener listener) {
        if (this.status.equals(FutureStatus.Timeout)) {
            listener.exception(new Exception("timeout"));
        } else if (this.status.equals(FutureStatus.Error)) {
            listener.exception(new Exception("已经出了异常"));
        } else if (this.status.equals(FutureStatus.Success)) {
            listener.complete(null);
        } else if (this.status.equals(FutureStatus.Canceled)) {
            listener.exception(new Exception("已经取消了"));
        } else {
            this.tlock.lock();
            try {
                this.listeners.add(listener);
            } finally {
                this.tlock.unlock();
            }
        }
    }

    /**
     * 当有进展的时候将会调用这个方法
     *
     * @param type
     *         : 类型，有可能是处理完了，也有可能是有异常
     * @param arg
     *         : 传递的参数
     */
    @Override
    public void fireEvent(EventType type, Object arg) {
        if (type.equals(EventType.Complete)) {
            this.status = FutureStatus.Success;
            this.tlock.lock();
            try {
                for (Listener lis : this.listeners) {
                    lis.complete(arg);
                }
            } finally {
                this.tlock.unlock();
            }
        } else if (type.equals(EventType.Exception)) {
            this.status = FutureStatus.Error;
            this.tlock.lock();
            try {
                for (Listener lis : this.listeners) {
                    lis.exception((Throwable) arg);
                }
            } finally {
                this.tlock.unlock();
            }
        }
    }

    /**
     * 取消当前任务的执行
     */
    @Override
    public void cancel() {
        this.status = FutureStatus.Canceled;
    }


    public synchronized List<Listener> getListeners() {
        return listeners;
    }

    /**
     * 获取当前future的状态
     *
     * @return
     */
    @Override
    public synchronized FutureStatus getStatus() {
        return this.status;
    }

    /**
     * 设置当前future的状态
     *
     * @param status
     */
    @Override
    public synchronized void setStatus(FutureStatus status) {
        this.status = status;
    }


    /**
     * 当超时的时候会调用的方法
     */
    @Override
    public synchronized void timeOut() {
        if (getStatus().equals(FutureStatus.Pendding) || getStatus().equals(FutureStatus.Running)) {
            if (this.getStatus().equals(FutureStatus.Running)) {
                this.cancel();
            }
            this.setStatus(FutureStatus.Timeout);
            service.execute(() -> AbstractFuture.this.fireEvent(EventType.Exception, new Exception("超时了")));
        }
    }

    /**
     * 当执行过程中出现异常调用的方法
     *
     * @param t
     */
    @Override
    public synchronized void exception(final Throwable t) {

        if (this.status.equals(FutureStatus.Running)) {
            this.setStatus(FutureStatus.Error);
            service.execute(() -> AbstractFuture.this.fireEvent(EventType.Exception, t));
        }
    }

    /**
     * 当处理成功之后会调用的方法
     *
     * @param arg
     */
    @Override
    public synchronized void success(final Object arg) {
        if (this.getStatus().equals(FutureStatus.Running)) {
            this.setStatus(FutureStatus.Success);
            service.execute(() -> AbstractFuture.this.fireEvent(EventType.Complete, arg));
        } else {
            log.warn("执行完了，但是状态不对啊");
        }
    }
}
