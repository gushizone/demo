package tk.gushizone.redis.redisson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/25 3:02 下午
 */
@Component
public class LockExecutor {

    @Autowired
    private RedissonOperator redissonOperator;

    public void execute(String lockKey, Runnable runnable) {
        // todo default config
        execute(lockKey, runnable, 0, 1, TimeUnit.MINUTES);
    }

    public void execute(String lockKey, Runnable runnable, int waitTime, int leaseTime, TimeUnit unit) {

        boolean gotLock = false;
        try {
            gotLock = redissonOperator.tryLock(lockKey, waitTime, leaseTime, unit);
            if (!gotLock) {
                throw new RuntimeException("服务忙，请稍后重试");
            }
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (gotLock) {
                redissonOperator.unlock(lockKey);
            }
        }
    }

}
