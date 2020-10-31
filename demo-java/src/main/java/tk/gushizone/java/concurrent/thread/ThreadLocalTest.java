package tk.gushizone.java.concurrent.thread;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-21 21:34
 */
public class ThreadLocalTest {


    public static final ThreadLocal<String> TENANT_ID = new ThreadLocal<>();





    public static void main(String[] args) throws InterruptedException {

        TENANT_ID.set("main");

        Thread t = new Thread(() -> {
            TENANT_ID.set("thread-0");

            doSomething();
        });
        t.start();
        t.join();

        doSomething();
    }

    private static void doSomething() {

        System.out.println(TENANT_ID.get());

    }



}
