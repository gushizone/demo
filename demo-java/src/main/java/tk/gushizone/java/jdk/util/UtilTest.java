package tk.gushizone.java.jdk.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
    public void messageFormat() throws ParseException {

        MessageFormat messageFormat = new MessageFormat("【{0}】{0}|{2}");
        List<String> messageValues = Lists.newArrayList("abc");

        String format = messageFormat.format(messageValues.toArray());
        // 【abc】abc|{2}
        System.out.println(format);

        Object[] parseObjs = messageFormat.parse(format);
        // [abc, null, null]
        System.out.println(Arrays.asList(parseObjs));

        // 数字会出现千分符
        String str = MessageFormat.format("价格：{0}", 10_000L);
        System.out.println(str);
    }

    @Test
    public void collection() {

        List<String> list = Collections.emptyList();
        Set<String> set = Collections.emptySet();
        Map<Integer, String> map = Collections.emptyMap();

        List<Integer> singletonList = Collections.singletonList(1);
        Set<Integer> singletonSet = Collections.singleton(1);
        Map<Integer, String> singletonMap = Collections.singletonMap(1, "foo");


        Collection<Integer> synchronizedCollection = Collections.synchronizedCollection(new ArrayList<>());
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        Set<Integer> synchronizedSet = Collections.synchronizedSet(new HashSet<>());
        Map<Integer, String> objectObjectMap = Collections.synchronizedMap(new HashMap<>());

        SimpleEntry simpleEntry = new SimpleEntry<>("key", "value");
        AbstractMap.SimpleImmutableEntry<String, String> simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry<>("key", "value");

    }

}
