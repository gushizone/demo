package tk.gushizone.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gushizone@gmail.com
 * @date 2021/5/13 6:09 下午
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PutMapping("/testSingle")
    public Long testSingle() {

        RedisAtomicLong hit = new RedisAtomicLong("demo:hit", stringRedisTemplate.getConnectionFactory());
        return hit.getAndIncrement();
    }



}
