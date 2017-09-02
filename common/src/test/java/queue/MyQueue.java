package queue;

/**
 * Created on 17/9/2 13:28 星期六.
 *
 * @author sd
 */
public class MyQueue {

    private int[] pQueue;
    private int iQueueLen;//队列里面有多少元素
    private int iQueueCapacity;//初始化大小
    private int iHead;
    private int iTail;

    public MyQueue(int capacity) {
        this.iQueueCapacity = capacity;
        this.pQueue = new int[capacity];
        clear();
    }

    /**
     * 清空
     */
    public void clear() {
        this.iHead = 0;
        this.iTail = 0;
        this.iQueueLen = 0;
    }

    /**
     * 判空
     *
     * @return
     */
    public boolean empty() {
        return iQueueLen == 0;
    }

    public boolean full() {
        return iQueueLen == iQueueCapacity;
    }

    /**
     * 获取队列长度
     */
    public int length() {
        return iQueueLen;
    }


    public boolean in(int element) {
        if (full()) {
            return false;
        }

        this.pQueue[iTail] = element;
        iTail = (++iTail) % iQueueCapacity;
        iQueueLen++;
        return true;
    }

    public int out() {
        if (empty()) {
            throw new NullPointerException("");
        }

        int element = pQueue[iHead];
        iHead = (++iHead) % iQueueCapacity;
        iQueueLen--;
        return element;
    }

    public void traverse() {
        System.out.println();
        for (int i = iHead; i < pQueue.length + iHead; i++) {
            System.out.println(pQueue[i % iQueueCapacity]);
        }
        System.out.println();
    }

}
