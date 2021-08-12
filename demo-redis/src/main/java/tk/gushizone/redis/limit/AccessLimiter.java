package tk.gushizone.redis.limit;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author gushizone@gmail.com
 * @date 2021/8/12 11:44 上午
 */
@Component
public class AccessLimiter {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;
    @Autowired
    private RedisScript<Boolean> limitScript;

    public void limit(String key, Integer threshold, Integer period) {

        Boolean ret = redisTemplate.execute(limitScript, Lists.newArrayList(key), threshold, period);
        if (!ret) {
            throw new RuntimeException("服务忙，请稍后重试：limit");
        }
    }


}
