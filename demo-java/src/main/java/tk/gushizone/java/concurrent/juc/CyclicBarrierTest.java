package tk.gushizone.java.concurrent.juc;

import com.google.common.collect.Lists;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏
 *
 * @author gushizone@gmail.com
 * @date 2020-09-24 23:21
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {


        CopyOnWriteArrayList<String> list = Lists.newCopyOnWriteArrayList();
        CyclicBarrier barrier = new CyclicBarrier(6);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {

                list.add(Thread.currentThread().getName());

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        barrier.await();
        System.out.println(list);

    }



}
