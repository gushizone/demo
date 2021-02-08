package tk.gushizone.spring.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import tk.gushizone.spring.SpringDemoApplication;
import tk.gushizone.spring.async.service.AsyncService;
import tk.gushizone.spring.async.service.SyncService;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Async 测试
 *
 * @author gushizone@gmail.com
 * @date 2020-01-30 18:16
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDemoApplication.class)
public class AsyncTest {

    @Resource
    private SyncService syncService;
    @Resource
    private AsyncService asyncService;

    /**
     * 异步方法本质是多线程调用
     */
    @Test
    public void thread() throws InterruptedException {

        for (int i = 0; i < 5; i++) {

            syncService.syncMethodNoReturn();
//            t.g.s.a.service.impl.SyncServiceImpl     : main
//            t.g.s.a.service.impl.SyncServiceImpl     : main
//            t.g.s.a.service.impl.SyncServiceImpl     : main
        }

        for (int i = 0; i < 20; i++) {

            asyncService.asyncMethodNoReturn();
//            t.g.s.a.service.impl.AsyncServiceImpl    : task-1
//            t.g.s.a.service.impl.AsyncServiceImpl    : task-2
//            t.g.s.a.service.impl.AsyncServiceImpl    : task-3
        }

        Thread.sleep(2_000);
    }

    /**
     * 异步调用
     * - 启用异步：@EnableAsync
     * - 标识异步方法：@Async
     * - 注意项：
     * 1. 异步方法是void或返回Future
     * 2. 因动态代理，this调用会导致异步失效
     * 3. 因多线程，事务（@Transactional）会失效
     */
    @Test
    public void async() throws ExecutionException, InterruptedException {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start("asyncMethodNoReturn");
        // 无返回值调用
        asyncService.asyncMethodNoReturn();
        stopWatch.stop();

        stopWatch.start("asyncMethod");
        // 有返回值调用
        Future<List<Integer>> listFuture = asyncService.asyncMethod();
        stopWatch.stop();
        stopWatch.start("asyncMethod - get");
        // 获取结果集
        List<Integer> list2 = listFuture.get();
        System.out.println(list2);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
//        StopWatch '': running time (millis) = 1033
//        -----------------------------------------
//        ms     %     Task name
//        -----------------------------------------
//        00022  002%  asyncMethodNoReturn
//        00000  000%  asyncMethod
//        01011  098%  asyncMethod - get
    }


}
