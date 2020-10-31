package tk.gushizone.java.concurrent.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-23 15:29
 */
public class AtomicTest {

    public static void main(String[] args) {

        AtomicInteger n = new AtomicInteger(1);
        AtomicReference<String> obj = new AtomicReference<>("");

        new Thread(() -> {
            n.incrementAndGet();
            n.addAndGet(1);

            obj.set("obj");
        }).start();
    }


}
