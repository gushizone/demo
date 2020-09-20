package tk.gushizone.java;

import org.junit.Test;

/**
 * @author gushizone@gmail.com
 * @date 2020-08-23 18:41
 */
public class JavaTest {

    @Test
    public void test() throws InterruptedException {


        while (true) {

            Thread.sleep(1000L);

            System.out.println(System.currentTimeMillis());
        }


    }

}
