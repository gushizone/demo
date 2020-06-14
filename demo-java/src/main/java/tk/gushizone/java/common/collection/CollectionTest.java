package tk.gushizone.java.common.collection;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-03 00:22
 */
public class CollectionTest {


    @Test
    public void partition() {

        List<Integer> list = Lists.newArrayListWithCapacity(10);
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        List<List<Integer>> partition = Lists.partition(list, 2);
        // [[0, 1], [2, 3], [4, 5], [6, 7], [8, 9]]
        System.out.println(partition);
    }

    @Test
    public void pair() {

        Pair<String, String> pair = Pair.of("Foo", "Bar");

        // Foo:Bar
        System.out.println(pair.getKey() + ":" +pair.getValue());
        System.out.println(pair.getLeft() + ":" +pair.getRight());
    }


}
