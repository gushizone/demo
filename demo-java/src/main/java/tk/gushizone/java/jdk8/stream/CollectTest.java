package tk.gushizone.java.jdk8.stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-03 16:49
 */
public class CollectTest {

    /**
     * 常见收集归约操作
     *
     * toList: 转换为list。
     * toSet: 转换为set。
     * toMap: 转换为map。
     * joining: 拼接为字符串。
     */
    @Test
    public void collect() {

        List<String> list = Arrays.asList("abc", "bc", "efg", null, "", "abc");

        List<String> listResults = list.stream()
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());

        Set<String> setResults = list.stream()
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toSet());

        Map<String, String> mapResults = list.stream()
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toMap(e -> e, String::toUpperCase, (a, b) -> a));

        String joinResult = list.stream()
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(", "));

        // [abc, bc, efg, abc]
        System.out.println(listResults);
        // [bc, abc, efg]
        System.out.println(setResults);
        // {bc=BC, abc=ABC, efg=EFG}
        System.out.println(mapResults);
        // abc, bc, efg, abc
        System.out.println(joinResult);
    }

    @Test
    public void toCollection() {

        List<String> list = Arrays.asList("abc", "bc", "efg", null, "", "abc");

        Set<String> setResults = list.stream()
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // [abc, bc, efg]
        System.out.println(setResults);
    }

}
