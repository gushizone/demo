package tk.gushizone.java.concurrent.juc;

import java.util.concurrent.Semaphore;

/**
 * 信号量
 *
 * @author gushizone@gmail.com
 * @date 2020-09-25 00:08
 */
public class SemaphoreTest {

    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(10);

        // 获取一个信号量
        semaphore.acquire();

        // 释放一个信号量
        semaphore.release();
    }


}
