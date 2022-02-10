package tk.gushizone.spring.test;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.Test;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

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


}
