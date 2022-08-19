package tk.gushizone.spring.test;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.Set;

/**
 * @author gushizone@gmail.com
 * @date 2021/9/2 5:25 下午
 */
public class UtilTest {


    @Test
    public void stopWatch() throws InterruptedException {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start("task1");

        System.out.println("doSomething...");
        Thread.sleep(1000L);

        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println(stopWatch.prettyPrint());

    }


    @Test
    public void asserts() {

        Assert.isTrue(false, "false");

    }

    @Test
    public void id() {

//        Snowflake snowflake = IdUtil.getSnowflake();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();

    }

    @Test
    public void test0() {

        StopWatch stopWatch = new StopWatch();
        Integer size = 10000;
        Set<String> set = Sets.newHashSetWithExpectedSize(size);
        stopWatch.start();
        for (int i = 0; i < size; i++) {
            String str = RandomUtil.randomString(6);
            set.add(str);
//            System.out.println(str);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(set.size());

    }


}
