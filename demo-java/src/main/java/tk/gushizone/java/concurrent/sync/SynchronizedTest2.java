package tk.gushizone.java.concurrent.sync;

import java.util.List;
import java.util.Vector;

public class SynchronizedTest2 {

    private static List<Integer> list = new Vector<>();

    /**
     * 关闭偏向锁,关闭生效延迟
     * -XX:-UseBiasedLocking -XX:BiasedLockingStartupDelay=0
     * <p>
     * 关闭偏向锁：1760
     * 开启偏向锁：1709
     * <p>
     * 适合一个线程反复活动锁, 一般可以提升 2% 作用的性能
     */
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) {
            list.add(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
