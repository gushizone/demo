package tk.gushizone.java.jdk.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-01 20:05
 */
public class UtilTest {


    @Test
    public void eq() {

        Object o1 = "a";
        Object o2 = "a";

        System.out.println(Objects.equals(o1, o2));
        // true
    }

    @Test
    public void messageFormat() {

        MessageFormat messageFormat = new MessageFormat("【{0}】{0}|{2}");
        List<String> messageValues = Lists.newArrayList("abc");
        // 【abc】abc|{2}
        System.out.println(messageFormat.format(messageValues.toArray()));
    }

    @Test
    public void collection() {

        List<String> list = Collections.emptyList();
        Set<String> set = Collections.emptySet();
        Map<Integer, String> map = Collections.emptyMap();

        List<Integer> singletonList = Collections.singletonList(1);
        Set<Integer> singletonSet = Collections.singleton(1);
        Map<Integer, String> singletonMap = Collections.singletonMap(1, "foo");


        SimpleEntry simpleEntry = new SimpleEntry<>("key", "value");
        AbstractMap.SimpleImmutableEntry<String, String> simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry<>("key", "value");
    }

}
