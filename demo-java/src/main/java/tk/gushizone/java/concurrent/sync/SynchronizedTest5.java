package tk.gushizone.java.concurrent.sync;

import java.util.List;
import java.util.Vector;

@SuppressWarnings("Duplicates")
public class SynchronizedTest5 {

    /**
     *
     * 开启逃逸分析,开启锁消除(默认)
     * -server -XX:+DoEscapeAnalysis -XX:+EliminateLocks 541
     * 关闭逃逸分析,关闭锁消除
     * -server -XX:-DoEscapeAnalysis -XX:-EliminateLocks 662
     */
    public static void main(String[] args) {

        List<Integer> list = new Vector<>();
        long start = System.currentTimeMillis();
        // 锁粗化后,synchronized 会在循环体外
        for (int i = 0; i < 1000_0000; i++) {
            synchronized (list) {
                list.add(i);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
