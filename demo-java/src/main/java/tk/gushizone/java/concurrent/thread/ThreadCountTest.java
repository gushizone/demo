package tk.gushizone.java.concurrent.thread;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-23 13:55
 */
@SuppressWarnings("Duplicates")
public class ThreadCountTest {

    public static int n = 1;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {

            for (int i = 0; i < 50; i++) {

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(n++);
            }
        }).start();

        new Thread(() -> {

            for (int i = 0; i < 50; i++) {

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(n++);
            }
        }).start();



//        Thread.sleep(1_100);
//        System.out.println(n);
    }


}
