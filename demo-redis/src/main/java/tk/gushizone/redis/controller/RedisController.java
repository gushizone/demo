package tk.gushizone.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.gushizone.redis.limit.AccessLimit;
import tk.gushizone.redis.limit.AccessLimiter;
import tk.gushizone.redis.redisson.LockExecutor;

/**
 * @author gushizone@gmail.com
 * @date 2021/5/13 6:09 下午
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AccessLimiter accessLimiter;
    @Autowired
    private LockExecutor lockExecutor;

    /**
     * 测试单线程
     */
    @PutMapping("/testSingle")
    public Long testSingle() {

        RedisAtomicLong hit = new RedisAtomicLong("demo:hit", stringRedisTemplate.getConnectionFactory());
        return hit.getAndIncrement();
    }

    @GetMapping("/ping")
    @AccessLimit(threshold = 2)
    public String hello(@RequestParam(required = false) String str) {
//        accessLimiter.limit("limit", 2, 1);
        return "pong";
    }


    @PostMapping("/lock")
    public String lock() {
        log.info("===> 进入方法");
        lockExecutor.execute("lock_demo", () -> {
            log.info("获取锁");

            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("释放锁");
        });
        log.info("===> 结束方法");
        return "end";
    }


}
