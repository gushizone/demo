package tk.gushizone.java.concurrent.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-20 23:50
 */
public class ThreadTest {


    /**
     * "Thread-0" #11 prio=5 os_prio=31 tid=0x00007fb28e07d800 nid=0x5a03 runnable [0x0000700001c0a000]
     * java.lang.Thread.State: RUNNABLE
     * at tk.gushizone.java.concurrent.thread.ThreadTest.doSomething(ThreadTest.java:23)
     * at tk.gushizone.java.concurrent.thread.ThreadTest.lambda$main$0(ThreadTest.java:12)
     * at tk.gushizone.java.concurrent.thread.ThreadTest$$Lambda$1/20132171.run(Unknown Source)
     * at java.lang.Thread.run(Thread.java:748)
     * <p>
     * "main" #1 prio=5 os_prio=31 tid=0x00007fb28d00c000 nid=0xe03 runnable [0x00007000004c2000]
     * java.lang.Thread.State: RUNNABLE
     * at tk.gushizone.java.concurrent.thread.ThreadTest.doSomething(ThreadTest.java:23)
     * at tk.gushizone.java.concurrent.thread.ThreadTest.main(ThreadTest.java:14)
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1. 继承 Thread, 重写 run()
        new FooThread().start();

        // 2. 实现 Runnable 接口, 实现 run()
        new Thread(() -> doSomething()).start();

        // 2. 实现 Callable 接口, 实现 call()
        FutureTask futureTask = new FutureTask(() -> "实现Callable");
        new Thread(futureTask).start();
        System.out.println(futureTask.get());


        doSomething();

//        new Thread().setDaemon(true);

    }

    private static void doSomething() {
        int i = 0;
        while (true) {
            i++;
        }
    }

    static class FooThread extends Thread {

        @Override
        public void run() {
            System.out.println("继承 Thread");
        }
    }

}
