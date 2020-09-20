package tk.gushizone.java.concurrent.sync;

@SuppressWarnings("Duplicates")
public class SynchronizedTest3 {

    public static void main(String[] args) {
        someMethod();
    }

    /**
     * 锁消除: JVM 分析锁对象为不可逃逸, 自动消除锁.
     */
    private static void someMethod() {

        Object object = new Object();
        synchronized(object) {
            System.out.println(object);
        }
    }
}
