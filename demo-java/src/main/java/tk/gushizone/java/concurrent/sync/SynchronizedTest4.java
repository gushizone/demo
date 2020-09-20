package tk.gushizone.java.concurrent.sync;

@SuppressWarnings("Duplicates")
public class SynchronizedTest4 {

    private Object object2 = null;

    public void someMethod2() {
        object2 = this.someMethod();
    }

    /**
     * 存在逃逸
     */
    private Object someMethod() {
        Object object = new Object();
        synchronized (object) {
            return object;
        }
    }
}
