package tk.gushizone.java.concurrent.juc.locks;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-24 17:53
 */
@Slf4j
public class ConditionTest {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();


    public static void main(String[] args) throws InterruptedException {

        Queue<String> queue = Lists.newLinkedList();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }

    static class Producer extends Thread {

        private Queue<String> queue;

        public Producer(Queue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            lock.lock();
            try {
                while (!queue.isEmpty()) {
                    condition.await();
                }

                String produce = UUID.randomUUID().toString();
                queue.add(produce);
                log.warn("生产：{}", produce);

                condition.signal();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }

    static class Consumer extends Thread {

        private Queue<String> queue;

        public Consumer(Queue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            lock.lock();
            try {
                while (queue.isEmpty()) {
                    condition.await();
                }

                log.warn("消费：{}", queue.remove());

                condition.signal();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


}
