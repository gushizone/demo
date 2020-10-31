package tk.gushizone.java.concurrent.thread;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-21 23:52
 */
public class ThreadInterruptTest {


    /**
     * fooThread.interrupt() 可以设置线程的中断标识, 而是否中断取决于代码逻辑.
     * 一般 Thread 自带阻塞性方法 都会响应这个方法.
     * 从上可知，InterruptedException 是线程中断机制的体现。
     */
    public static void main(String[] args) {


        Thread fooThread = new Thread(() -> {

            int i = 0;
            while (true) {
                i++;
                if (Thread.interrupted()) {
                    System.out.println("当前线程需要中断");
                }
            }
        });
        fooThread.start();

        fooThread.interrupt();
    }







}
