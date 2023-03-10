package tk.gushizone.java.concurrent.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TTL 当子线程来自线程池，且子线程修改了线程变量，线程复用时，拿到的依然是父线程的线程变量
 * https://github.com/alibaba/transmittable-thread-local
 *
 * 注意: 一定要修饰线程程等(TtlExecutors), 否则可能出现线程变量错乱, 或内存泄漏等.
 *
 * @author gushizone@gmail.com
 * @date 2021/2/11 9:02 下午
 */
public class TransmittableThreadLocalTest {

    public static final ThreadLocal<String> CODE = new TransmittableThreadLocal<>();

    public static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    private static void print() {
        System.out.println(Thread.currentThread().getName() + " : " + CODE.get());
    }

    @Test
    public void task() {

        CODE.set("main");

        Runnable task = () -> {
            print();
            CODE.set("t1");
            print();
        };

        EXECUTOR.execute(TtlRunnable.get(task));

        EXECUTOR.execute(task);
    }

    @Test
    public void service() {

        CODE.set("main");

        Runnable task = () -> {
            print();
            CODE.set("t1");
            print();
        };

        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(EXECUTOR);

        ttlExecutorService.execute(task);
        ttlExecutorService.execute(task);
    }

    @Test
    public void service0() {

        CODE.set("main");

        Runnable task1 = () -> {
            print();
        };

        Runnable task2 = () -> {
            print();
        };

        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(EXECUTOR);

        ttlExecutorService.execute(task1);
        CODE.remove();
        ttlExecutorService.execute(task2);
    }

    /**
     * fixme: 错误使用示例
     */
    @Test
    public void service1() {

        CODE.set("main");

        Runnable task1 = () -> {
            print();
        };

        Runnable task2 = () -> {
            print();
        };

        EXECUTOR.execute(task1);
        CODE.remove();
        EXECUTOR.execute(task2);
        CODE.remove();
        CODE.set("main2");
        EXECUTOR.execute(task2);

//        pool-1-thread-1 : main
//        pool-1-thread-1 : main
//        pool-1-thread-1 : main
    }

}
