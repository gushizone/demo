package tk.gushizone.java.common.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-03 22:26
 */
public class StringTest {


    @Test
    public void test() {

        List<String> list = Arrays.asList("abc", "bc", "efg", null, "", "abc");

        System.out.println(StringUtils.join(list, "，"));
    }

}
