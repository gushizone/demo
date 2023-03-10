package tk.gushizone.java.jvm.reference;

import cn.hutool.core.util.ReflectUtil;
import org.junit.Test;

public class ThreadLocalGCTest {

    /**
     * entry 会被回收
     */
    @Test
    public void test1() {
        ThreadLocal<BigObject> threadLocal = new ThreadLocal<>();
        BigObject obj1 = new BigObject();
        threadLocal.set(obj1);

        System.out.println("threadLocal = " + threadLocal);
        System.out.println("=================");

        print();

        threadLocal.remove();

        print();
    }

    /**
     * entry 不会被回收(存在强引用)
     */
    @Test
    public void test2() {

        ThreadLocal<BigObject> threadLocal = new ThreadLocal<>();
        BigObject obj1 = new BigObject();
        threadLocal.set(obj1);

        System.out.println("threadLocal = " + threadLocal);
        System.out.println("=================");

        print();

        System.gc();

        print();
    }

    /**
     * entry 的 key 被回收了, value 没回收
     */
    @Test
    public void test3() {

        ThreadLocal<BigObject> threadLocal = new ThreadLocal<>();
        BigObject obj1 = new BigObject();
        threadLocal.set(obj1);

        System.out.println("threadLocal = " + threadLocal);
        System.out.println("=================");

        print();

        threadLocal = null;
        System.gc();

        print();
    }



    private static void print() {
        Object threadLocals = ReflectUtil.getFieldValue(Thread.currentThread(), "threadLocals");
        Object size = ReflectUtil.getFieldValue(threadLocals, "size");
        System.out.println("size = " + size);

        Object[] table = (Object[]) ReflectUtil.getFieldValue(threadLocals, "table");
        for (Object o : table) {
            Object value = ReflectUtil.getFieldValue(o, "value");
            if (value instanceof BigObject) {
                Object referentKey = ReflectUtil.getFieldValue(o, "referent");
                System.out.println("referentKey = " + referentKey);
            }
        }
        System.out.println("=======");
    }

    private static class BigObject {
        private byte[] data = new byte[1024 * 1024];
    }
}
