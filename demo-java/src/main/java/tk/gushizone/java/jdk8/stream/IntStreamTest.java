package tk.gushizone.java.jdk8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-03 21:27
 */
public class IntStreamTest {


    @Test
    public void test() {

        List<String> list = Arrays.asList("abc", "bc", "efg");

        IntStream.range(0, list.size())
                .forEach(e -> System.out.println(list.get(e)));
    }


}
