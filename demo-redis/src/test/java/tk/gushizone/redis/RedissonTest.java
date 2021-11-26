package tk.gushizone.redis;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.redis.redisson.LockExecutor;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/25 2:42 下午
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private LockExecutor lockExecutor;


    @Test
    public void test() {


    }



}
