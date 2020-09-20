package tk.gushizone.java.concurrent.juc.threadpool;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * junit 默认是不支持多线程测试: JUnitCore 不会等待子线程都完成了再关闭主线程
 * <p>
 * 当前任务若未完成,则不会开始下次执行.
 * FixedRate : 固定速度执行
 * FixedDelay: 固定延迟执行
 */
public class ScheduledThreadPoolExecutorTest {

    @Test
    public void schedule() throws ExecutionException, InterruptedException {

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
                10,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        // 延时3秒之后再去执行任务
        executor.schedule(() -> System.out.println("aaa"), 3, TimeUnit.SECONDS);

        // 延时4秒之后再去执行任务，可以返回执行结果
        ScheduledFuture<String> future = executor.schedule(() -> "bbb", 4, TimeUnit.SECONDS);
        String s = future.get();
        System.out.println(s);

    }

    /**
     * FixedRate : 固定速度执行
     */
    @Test
    public void scheduleAtFixedRate() throws InterruptedException {

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
                10,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        executor.scheduleAtFixedRate(
                () -> {
                    System.out.println("scheduleAtFixedRate " + new Date());
                    try {
                        Thread.sleep(1000L);
//                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                // 第一次执行任务时，延时多久
                0,
                // 每隔多久执行这个任务
                3, TimeUnit.SECONDS
        );

        // 防 junit 退出
        Thread.sleep(20_000);
    }

    /**
     * FixedDelay: 固定延迟执行
     */
    @Test
    public void scheduleWithFixedDelay() throws InterruptedException {

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
                10,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        executor.scheduleWithFixedDelay(
                () -> {
                    System.out.println("scheduleWithFixedDelay " + new Date());
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                // 第一次执行任务时，延时多久
                0,
                // 每次执行完任务之后，延迟多久再次执行这个任务
                3, TimeUnit.SECONDS
        );

        // 防 junit 退出
        Thread.sleep(20_000);
    }
}
