package pro.tools.http.netty.client;

import pro.tools.http.netty.clientpool.ClientPool;
import pro.tools.http.netty.http.Request;
import pro.tools.http.netty.lifecycle.Lifecycle;

/**

 */
public interface Client extends Lifecycle {
    /**
     * 获取所属的pool
     *
     * @return
     */
    ClientPool getClientPool();

    /**
     * 获取远程地址
     *
     * @return
     */
    String getRemoteHost();

    /**
     * 处理请求
     *
     * @param request
     *
     * @return
     */
    void request(Request request);

    /**
     * 取消正在运行的request
     */
    void cancel();

    /**
     * 返回正在运行的request
     *
     * @return
     */
    Request getRequest();

    /**
     * 设置正在运行的request
     *
     * @param request
     */
    void setRequest(Request request);

    /**
     * 用于返回当前client的状态
     *
     * @return
     */
    ClientStatus getStatus();

    /**
     *  用于设置当前client的状态
     *
     * @param status
     */
    void setStatus(ClientStatus status);

    /**
     * 当当前client可以用的时候调用的方法，将这个client加入到pool啥的
     */
    void ready();


    enum ClientStatus {
        Starting, Ready, Working, Stopped
    }

}
