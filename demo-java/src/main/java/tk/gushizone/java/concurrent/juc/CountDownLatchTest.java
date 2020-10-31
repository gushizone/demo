package tk.gushizone.java.concurrent.juc;

import com.google.common.collect.Lists;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 倒数计数器
 *
 * @author gushizone@gmail.com
 * @date 2020-09-24 22:49
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        CopyOnWriteArrayList<String> list = Lists.newCopyOnWriteArrayList();
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {

                list.add(Thread.currentThread().getName());

                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.println(list);


    }





}
