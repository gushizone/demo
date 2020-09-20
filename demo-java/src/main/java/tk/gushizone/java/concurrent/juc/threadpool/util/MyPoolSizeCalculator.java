package tk.gushizone.java.concurrent.juc.threadpool.util;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyPoolSizeCalculator extends PoolSizeCalculator {

    /**
     * 线程池大小自动计算
     * <p>
     * Target queue memory usage (bytes): 100000
     * createTask() produced tk.gushizone.java.concurrent.juc.threadpool.util.AsynchronousTask which took 40 bytes in a queue
     * Formula: 100000 / 40
     * * Recommended queue capacity (bytes): 2500
     * Number of CPU: 12
     * Target utilization: 1
     * Elapsed time (nanos): 3000000000
     * Compute time (nanos): 2998692000
     * Wait time (nanos): 1308000
     * Formula: 12 * 1 * (1 + 1308000 / 2998692000)
     * * Optimal thread count: 12
     *
     * <pre>{@code
     *
     * ThreadPoolExecutor executor = new ThreadPoolExecutor(
     *        12,
     *        12,
     *        10L,
     *        TimeUnit.SECONDS,
     *        new LinkedBlockingQueue<>(2500),
     *        Executors.defaultThreadFactory(),
     *        new ThreadPoolExecutor.AbortPolicy()
     * );
     * }</pre>
     */
    public static void main(String[] args) {

        MyPoolSizeCalculator calculator = new MyPoolSizeCalculator();
        calculator.calculateBoundaries(
                // CPU目标利用率
                new BigDecimal(1.0),
                // blockingqueue占用的内存大小，byte
                new BigDecimal(100_000));
    }

    @Override
    protected long getCurrentThreadCPUTime() {
        // 当前线程占用的总时间
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }

    @Override
    protected Runnable creatTask() {
        // 测试任务
        return new AsynchronousTask();
    }

    @Override
    protected BlockingQueue createWorkQueue() {
        // 期望使用的 BlockingQueue
        return new LinkedBlockingQueue<>();
    }

}

class AsynchronousTask implements Runnable {

    @Override
    public void run() {
        // 测试任务
        // System.out.println(Thread.currentThread().getName());
    }
}