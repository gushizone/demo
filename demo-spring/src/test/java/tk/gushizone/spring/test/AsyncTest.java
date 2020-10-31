package tk.gushizone.spring.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.spring.SpringDemoApplication;
import tk.gushizone.spring.async.service.SyncService;

import javax.annotation.Resource;

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
    SyncService syncService;

    @Test
    public void test() {

//        List<?> results = syncService.test();
        syncService.test0();
    }

}
