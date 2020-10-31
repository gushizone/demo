package tk.gushizone.java.concurrent.thread;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-20 23:37
 */
public class ThreadExceptionTest {

    public static void main(String[] args) throws InterruptedException {

        try {
            new Thread(() -> {
                throw new RuntimeException();
            }).start();
            Thread.sleep(1000);
        } catch (Exception e) {
            // 线程只能捕捉自己的异常(因为线程拥有独立的方法栈)
        }

        Thread.sleep(1000);
    }



}
