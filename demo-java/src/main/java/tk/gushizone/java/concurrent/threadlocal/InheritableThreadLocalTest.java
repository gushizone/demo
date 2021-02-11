package tk.gushizone.java.concurrent.threadlocal;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gushizone@gmail.com
 * @date 2021/2/11 8:01 下午
 */
public class InheritableThreadLocalTest {

    public static final ThreadLocal<String> CODE = new InheritableThreadLocal<>();

    public static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    private static void print() {
        System.out.println(Thread.currentThread().getName() + " : " + CODE.get());
    }

    /**
     * ITL 子线程可以拿到父线程的线程变量
     */
    @Test
    public void advantage() throws InterruptedException {

        CODE.set("main");

        Thread t1 = new Thread(() -> {
            print();
            CODE.set("t1");
            print();
        });
        t1.start();

        t1.join();
        print();
    }

    /**
     * ITL 当子线程来自线程池，且子线程修改了线程变量，线程复用时，拿到的不是父线程的线程变量
     */
    @Test
    public void disadvantage() {

        CODE.set("main");

        Runnable task = () -> {
            print();
            CODE.set("t1");
            print();
        };

        EXECUTOR.execute(task);

        EXECUTOR.execute(task);
    }

}
