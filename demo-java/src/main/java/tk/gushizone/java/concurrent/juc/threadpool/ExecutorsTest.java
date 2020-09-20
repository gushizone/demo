package tk.gushizone.java.concurrent.juc.threadpool;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors
 */
public class ExecutorsTest {

    @Test
    public void singleThreadExecutor() {

        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        ExecutorService executorService2 = Executors.newFixedThreadPool(3);
        ExecutorService executorService3 = Executors.newCachedThreadPool();
        ExecutorService executorService4 = Executors.newSingleThreadExecutor();
        ExecutorService executorService5 = Executors.newWorkStealingPool();

    }


}
