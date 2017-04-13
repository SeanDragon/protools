package sd;

import java.util.Arrays;

/**
 * Created by tuhao on 2017/4/12.
 */
public class Test_20170412 {

    public static void main(String[] args) {
        Thread[] threads = {
                // Pass a lambda to a thread
                new Thread(() -> {
                    System.out.println("Test_20170412.main");
                })
        };
        ((Runnable) () -> System.out.println("Test_20170412.main")).run();

        ((Runnable) () -> System.out.println("123")).run();

        ((Runnable) () -> {
            System.out.println("Test_20170412.main");
        }).run();

// Start all threads
        Arrays.stream(threads).forEach(Thread::start);

// Join all threads
        Arrays.stream(threads).forEach(t -> {
            try { t.join(); }
            catch (InterruptedException ignore) {}
        });
    }
}
