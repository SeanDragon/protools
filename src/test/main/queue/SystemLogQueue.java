package queue;
import org.junit.Test;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * 用于记录日志的队列，queue 基于链接节点的无界线程安全队列
 * 此队列按照 FIFO（先进先出）原则对元素进行排序，详见J2SE_API或JDK
 */
public class SystemLogQueue {

    private static Queue<Log> log_Queue;

    static {
        if (null == log_Queue) {
            log_Queue = new ConcurrentLinkedQueue<Log>();
        }
    }


    /**
     * 初始化创建队列
     **/
    public static void init() {
        if (null == log_Queue) {
            log_Queue = new ConcurrentLinkedQueue<Log>(); //基于链接节点的无界线程安全队列
        }
    }


    /**
     * 添加到队列方法，将指定元素插入此队列的尾部。
     *
     * @param log Log对象
     * @return 成功返回true，否则抛出 IllegalStateException
     */
    public static boolean add(Log log) {
        return (log_Queue.add(log));
    }


    /**
     * 获取并移除此队列的头 ，如果此队列为空，则返回 null
     */
    public static Log getPoll() {
        return (log_Queue.poll());
    }

    /**
     * 获取但不移除此队列的头；如果此队列为空，则返回 null
     **/
    public static Log getPeek() {
        return (log_Queue.peek());
    }

    /**
     * 判断此队列是否有元素 ，没有返回true
     **/
    public static boolean isEmpty() {
        return (log_Queue.isEmpty());
    }


    /**
     * 获取size,速度慢，对性能影响巨大，不能做为频繁判断
     **/
    public static int getQueueSize() {
        return (log_Queue.size());
    }


    @Test
    public void dosome() {
        System.out.println("队列是否有元素：" + !isEmpty());
        Log log = new Log();
        log_Queue.add(log);

        System.out.println("队列是否有元素：" + !isEmpty());
        Log log2 = new Log();
        log2.setDate(new Date());
        log2.setValue("哈哈哈");
        log_Queue.add(log2);
        System.out.println("队列元素个数：" + getQueueSize());

        Log l = getPeek();
        System.out.println("\n获取队列数据：" + l.getValue() + "---" + l.getDate());
        System.out.println("队列元素个数：" + getQueueSize());

        for (int i = 0; i < 2; i++) {
            Log l2 = getPoll();
            if (l2 != null) {
                System.out.println("\n获取队列数据并删除：" + l2.getValue() + "---" + l2.getDate());
            }
            System.out.println("队列元素个数：" + getQueueSize());
        }
    }
}