package tk.gushizone.java.concurrent.thread;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-22 23:20
 */
public class VolatileTest {

    public static boolean flag = true;
//    public static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            synchronized (VolatileTest.class) {
                while (flag) {
//                System.out.println(System.currentTimeMillis());
                }
            }
        }).start();

        Thread.sleep(100);
        flag = false;
    }


}
