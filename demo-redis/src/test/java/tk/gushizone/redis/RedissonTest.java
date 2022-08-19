package tk.gushizone.redis;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.FstCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.MarshallingCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.redis.entity.Item;
import tk.gushizone.redis.entity.Message;
import tk.gushizone.redis.redisson.LockExecutor;

import java.lang.reflect.Type;
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
    private RedissonClient redisson;
    @Autowired
    private LockExecutor lockExecutor;


    @Test
    public void test1() {

//        RMap<String, String> map = redisson.getMap("testMap", Singleton.get(StringCodec.class));
        // 有淘汰机制的数据结构, 序列化方式不是简单的 string
//        RMapCache<String, String> map = redisson.getMapCache("testMap", Singleton.get(StringCodec.class));

//        for (int i = 0; i < 10; i++) {
//
//            map.put(RandomUtil.randomNumbers(6), JSONUtil.toJsonStr(new Item((long) i, Lists.newArrayList("123", "abc", "中文"))));
//
//        }


//        RSet<String> set = redisson.getSet("testSet", Singleton.get(StringCodec.class));
//
//        for (int i = 0; i < 10; i++) {
//
//            set.add(JSONUtil.toJsonStr(new Item((long) i, Lists.newArrayList("123", "abc", "中文"))));
//        }


//        RList<String> list = redisson.getList("testList", Singleton.get(StringCodec.class));
//        for (int i = 0; i < 10; i++) {
//
//            list.add(JSONUtil.toJsonStr(new Item((long) i, Lists.newArrayList("123", "abc", "中文"))));
//        }
//        String s = list.get(0);
//        list.remove(0);


        RBlockingDeque<String> queue = redisson.getBlockingDeque("testQueue", Singleton.get(StringCodec.class));
        for (int i = 0; i < 10; i++) {

            queue.add(JSONUtil.toJsonStr(new Item((long) i, Lists.newArrayList("123", "abc", "中文"))));
        }

    }



    @Test
    public void test() {

        Message<Item> value = Message.<Item>builder()
                .id("123abc中文")
                .body(Item.builder()
                        .id(123L)
                        .address(Lists.newArrayList("上海", "合肥"))
                        .build())
                .build();

//        redisson.getBucket("redisson-test", new JsonJacksonCodec()).set(value);



//        TypeReference<Message<Item>> typeReference = new TypeHandler().typeReference();
//        TypeReference<Message<Item>> typeReference = new TypeReference<Message<Item>>() {};
        TypeReference<Message<Item>> typeReference = new TypeHandler();

        redisson.getBucket("redisson-test", new TypedJsonJacksonCodec(typeReference)).set(value);
//        redisson.getBucket("redisson-test", new TypedJsonJacksonCodec(new TypeReference() {})).set(value);
//        redisson.getBucket("redisson-test", new TypedJsonJacksonCodec(new TypeReference<Message<Item>>() {})).set(value);

//        redisson.getBucket("redisson-test", new FstCodec()).set(value);
//        redisson.getConfig().setCodec(new JsonJacksonCodec());

//        Message<Item> message = (Message<Item>) redisson.getBucket("redisson-test", new JsonJacksonCodec()).get();

        Message<Item> message = (Message<Item>) redisson.getBucket("redisson-test", new TypedJsonJacksonCodec(typeReference)).get();
//        Message<Item> message = (Message<Item>) redisson.getBucket("redisson-test", new TypedJsonJacksonCodec(new TypeReference<Message<Item>>() {})).get();

//        Message<Item> message = JSONUtil.toBean(obj, Message.class);
//        Message<Item> message = JSONUtil.toBean(obj, new TypeReference<Message<Item>>() {
//            @Override
//            public Type getType() {
//                return super.getType();
//            }
//        }, false);
//        System.out.println(message);
    }


    public static class TypeHandler extends AbstractTypeHandler<Message<Item>> {
    }


    public static abstract class AbstractTypeHandler<T extends Message<?>> extends TypeReference<T> {

    }



}
