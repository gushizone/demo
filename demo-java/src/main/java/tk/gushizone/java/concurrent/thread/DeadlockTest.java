package tk.gushizone.java.concurrent.thread;

/**
 * 死锁演示
 *
 * @author gushizone@gmail.com
 * @date 2020-09-22 22:30
 */
public class DeadlockTest {

    public static final String LOCK_1 = "LOCK_1";
    public static final String LOCK_2 = "LOCK_2";

    /**
     * "Thread-0" #11 prio=5 os_prio=31 tid=0x00007f8cc796e800 nid=0xa403 waiting for monitor entry [0x0000700009b6d000]
     * java.lang.Thread.State: BLOCKED (on object monitor)
     * at tk.gushizone.java.concurrent.thread.DeadlockTest.lambda$main$0(DeadlockTest.java:26)
     * - waiting to lock <0x000000076aca3400> (a java.lang.String)
     * - locked <0x000000076aca3438> (a java.lang.String)
     * at tk.gushizone.java.concurrent.thread.DeadlockTest$$Lambda$1/20132171.run(Unknown Source)
     * at java.lang.Thread.run(Thread.java:748)
     * <p>
     * "main" #1 prio=5 os_prio=31 tid=0x00007f8cc200b000 nid=0xe03 waiting for monitor entry [0x0000700008425000]
     * java.lang.Thread.State: BLOCKED (on object monitor)
     * at tk.gushizone.java.concurrent.thread.DeadlockTest.main(DeadlockTest.java:38)
     * - waiting to lock <0x000000076aca3438> (a java.lang.String)
     * - locked <0x000000076aca3400> (a java.lang.String)
     */
    public static void main(String[] args) {

        new Thread(() -> {

            synchronized (LOCK_2) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK_1) {
                    System.out.println("Thread-0");
                }
            }
        }).start();

        synchronized (LOCK_1) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (LOCK_2) {
                System.out.println("main");
            }
        }
    }


}
