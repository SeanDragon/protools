package queue.test;

import org.junit.Test;
import pro.tools.system.ToolClassSearch;
import queue.MyQueue;

import java.util.Set;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created on 17/9/2 13:48 星期六.
 *
 * @author sd
 */
public class TestMyQueue {

    @Test
    public void test2() {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        System.out.println(longAdder.longValue());
        longAdder.increment();
        System.out.println(longAdder.longValue());
        System.out.println(longAdder.sum());
        System.out.println(longAdder.toString());
        System.out.println(longAdder.longValue());

    }

    @Test
    public void test4() {
        Set<Class> allClazz = ToolClassSearch.getAllClazz();
        System.out.println(allClazz);
    }

    @Test
    public void test3() {

        int A = Integer.MAX_VALUE;
        int B = Integer.MAX_VALUE - 129;

        System.out.println(A);
        System.out.println(B);

        A = A ^ B;

        B = A ^ B;

        A = A ^ B;

        System.out.println(A);
        System.out.println(B);


        A = A ^ B;

        B = A ^ B;

        A = A ^ B;


        System.out.println(A);
        System.out.println(B);
    }

    @Test
    public void test1() {

        MyQueue myQueue = new MyQueue(5);
        myQueue.in(1);
        myQueue.in(2);
        myQueue.in(3);
        myQueue.in(4);
        myQueue.in(5);

        myQueue.traverse();

        System.out.println();

        System.out.println(myQueue.out());
        System.out.println(myQueue.out());
        System.out.println(myQueue.out());

        System.out.println();

        myQueue.traverse();

        myQueue.clear();
        myQueue.in(6);
        myQueue.traverse();
    }
}
