package tk.gushizone.java.jdk.arithmetic;

import org.junit.Test;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-03 01:57
 */
public class NumberTest {

    @Test
    public void convert() {

        Long a = 1L;
        Integer b = Math.toIntExact(a);
        Integer c = a.intValue();
    }

}
