package tk.gushizone.java.concurrent.juc.locks;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-24 16:54
 */
@Slf4j
public class ReentrantLockTest {


    public static int i = 0;


    /**
     * 可重入性，互斥性
     */
    @Test
    public void test1() throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        Runnable increase = () -> {
            for (int j = 0; j < 1_000; j++) {
                try {
                    lock.lock();
                    lock.lock();
                    i++;
                } finally {
                    lock.unlock();
                    lock.unlock();
                }
            }
        };

        Thread t1 = new Thread(increase);
        Thread t2 = new Thread(increase);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(i);
    }


    /**
     * 公平性：谁先发锁请求，谁先拿锁
     */
    @Test
    public void testFair() throws InterruptedException {

        ReentrantLock lock = new ReentrantLock(true);

        Runnable runnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + "开始运行");
                Thread.sleep(1L);
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "拿到锁");
                Thread.sleep(1000L);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            }

        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }

    @Test
    public void test() throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        Runnable runnable = () -> {


//            lock.lock();
//
//            try {
//                Thread.sleep(100_000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                lock.unlock();
//
//            }

//            synchronized (lock) {
//                try {
//                    Thread.sleep(100_000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }


}
