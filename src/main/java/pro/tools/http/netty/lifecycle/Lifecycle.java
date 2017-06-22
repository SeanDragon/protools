package pro.tools.http.netty.lifecycle;

/**
 * 用于管理组件的生命周期
 */
public interface Lifecycle {
    /**
     * 启动组件
     */
    void start();

    /**
     * 停止组件
     */
    void stop();
}
