package tk.gushizone.redis.test;

import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.redis.RedisApplication;
import tk.gushizone.redis.util.RedisOperator;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-10-03 14:32
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTemplateTest {


    // 不推荐使用
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisOperator redisOperator;

    @Test
    public void test() {

        // 默认序列化会出现 \xac\xed\x00\x05t\x00\x02
        redisTemplate.opsForValue().set("k", "v");

        String value = (String) redisTemplate.opsForValue().get("k");
        System.out.println(value);

    }

    @Test
    public void test1() {

        stringRedisTemplate.opsForValue().set("key", "value");

    }

    /**
     * 布隆过滤器 - guava 版
     */
    @Test
    public void test2() {

        BloomFilter bf = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")),
                10_0000,
                0.0001);

        for (int i = 0; i < 10_0000; i++) {
            bf.put(String.valueOf(i));
        }

        int count = 0;
        for (int i = 0; i < 1_0000; i++) {

            if (bf.mightContain("Foo" + i)) {
                count++;
            }
        }

        System.out.println("误判次数：" + count);

    }

    /**
     * 批量获取
     */
    @Test
    public void test3() {

        List<String> stringList1 = redisOperator.mget(Lists.newArrayList("a", "b", "c"));

        List<Object> stringList2 = redisOperator.batchGet(Lists.newArrayList("a", "b", "c"));


    }



}
