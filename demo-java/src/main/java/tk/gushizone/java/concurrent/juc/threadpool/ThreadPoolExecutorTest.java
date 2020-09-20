package tk.gushizone.java.concurrent.juc.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor
 */
public class ThreadPoolExecutorTest {


    /**
     * - corePoolSize : 核心线程数
     * - maximumPoolSize : 最大线程数
     * - keepAliveTime : 默认情况下指的是非核心线程的空闲时间(超过会被回收); 如果allowCoreThreadTimeOut=true, 核心线程/非核心线程允许的空闲时间
     * - unit : keepAliveTime 的单位
     * - workQueue : 储存等待执行的任务,传入 BlockingQueue (队列满了才会创建非核心线程, 需要懂得分析)
     * - threadFactory : 默认 defaultThreadFactory, 创建的线程拥有相同优先级, 非守护线程, 有线程名称;
     * privilegedThreadFactory 在 defaultThreadFactory 的基础上,可以让运行在这个线程中的任务拥有和这个线程相同的访问控制和 ClassLoader
     * - handler : 拒绝任务时的策略
     * 1. AbortPolicy : 默认, 抛异常
     * 2. CallerRunsPolicy : 用调用者所在的线程执行任务
     * 3. DiscardOldestPolicy : 丢弃队列中最靠前的任务
     * 4. DiscardPolicy : 丢弃当前任务
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(
                        5,
                        10,
                        10L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(100),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy()
                );
        executor.allowCoreThreadTimeOut(true);

        // 提交任务, 交给线程池执行
        executor.execute(() -> System.out.println("线程池测试"));
        executor.execute(() -> System.out.println("线程池测试2"));

        // 提交任务, 交给线程池执行,有返回值
        Future<String> future = executor.submit(() -> "测试submit");
        String s = future.get();
        System.out.println(s);

        // 优雅关闭线程池,等待任务都执行完成
        executor.shutdown();
        // 强制关闭线程池
//        executor.shutdownNow();


//        监控 API
//        executor.getTaskCount();
//        executor.getCompletedTaskCount();
//        executor.getPoolSize();
//        executor.getActiveCount();

//        线程池状态: RUNNING | SHUTDOWN | STOP | TIDYING | TERMINATED
        executor.isShutdown();
    }
}
