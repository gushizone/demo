package tk.gushizone.redis.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/25 2:51 下午
 */
@Slf4j
@Component
public class RedissonOperator {

    @Autowired
    private RedissonClient redissonClient;

    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (Exception e) {

            log.error("lock error: {}", e.getMessage(), e);
            return false;
        }
    }

    public void unlock(String lockKey) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            lock.unlock();
        } catch (Exception e) {
            log.error("unlock error: {}", e.getMessage(), e);
        }
    }

}
