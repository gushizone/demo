package tk.gushizone.java.concurrent.threadlocal;

import org.junit.Test;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-21 21:34
 */
public class ThreadLocalTest {

    public static final ThreadLocal<String> CODE = new ThreadLocal<>();

    private static void print() {
        System.out.println(Thread.currentThread().getName() + " : " + CODE.get());
    }

    /**
     * TL 可以确保不同的线程设置和获取自己的变量
     */
    @Test
    public void advantage() throws InterruptedException {

        CODE.set("main");

        Thread t1 = new Thread(() -> {

            CODE.set("t1");
            print();
        });
        t1.start();

        t1.join();
        print();
    }

    /**
     * TL 子线程无法拿到父线程的线程变量
     */
    @Test
    public void disadvantage() throws InterruptedException {

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


}
