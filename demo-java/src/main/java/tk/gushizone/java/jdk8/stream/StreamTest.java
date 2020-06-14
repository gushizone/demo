package tk.gushizone.java.jdk8.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * JDK8 新特性 : Stream流
 * 用途: 可以让你以一种声明的方式处理数据，简化代码。
 * <p>
 * 流操作一般分为三步:
 * 1. 流程化
 * 2. 中间操作
 * 3. 完成操作
 *
 * @author gushizone@gmail.com
 * @date 2019-05-14 10:55
 */
public class StreamTest {


    /**
     * 简单示例
     * <p>
     * stream: 为集合创建串行流。 -- 流化
     * filter: 筛选出元素。 -- 中间操作
     * collect: 收集元素。 -- 完成操作
     */
    @Test
    public void simpleCase() {

        List<String> strings = Arrays.asList("bc", "efg", "abc", "", "jkl");

        List<String> results = strings.stream()
                .filter(string -> !string.isEmpty())
                .sorted()
                .collect(Collectors.toList());

        // [abc, bc, efg, jkl]
        System.out.println(results);
    }

    /**
     * 流化
     * <p>
     * stream: 串行流
     * parallelStream: 并行流
     */
    @Test
    public void stream() {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        // 12345
        list.stream().sorted().forEach(System.out::print);
        System.out.println();
        // 31542 -- 顺序不定
        list.parallelStream().forEach(System.out::print);
        System.out.println();
        // 12345
        list.parallelStream().forEachOrdered(System.out::print);
    }


    /**
     * 中间操作
     * <p>
     * distinct: 去重。
     * map: 映射每个元素执行函数，并返回。
     * sorted: 排序。
     * limit: 截取限制数量。
     */
    @Test
    public void middle() {

        List<Integer> list = Arrays.asList(3, 2, 2, 9, 7, 1, 5);

        List<Integer> results = list.stream()
                .distinct()
                .map(i -> i * i)
                .sorted()
                .limit(5)
                .collect(Collectors.toList());

        // [1, 4, 9, 49, 81]
        System.out.println(results);
    }

    /**
     * 完成操作
     * <p>
     * collect: 收集归约。
     * reduce: 聚合归约。
     * match: 匹配判断。
     */
    @Test
    public void performs() {

        List<Integer> list = Arrays.asList(3, 2, 2, 9, 7, 1, 5);

        List<Integer> collectResults = list.stream()
                .sorted()
                .collect(Collectors.toList());

        Integer reduceResult = list.stream()
                .reduce(0, Integer::sum);

        boolean matchResult = list.stream()
                .anyMatch(e -> e > 10);

        long countResult = list.stream()
                .distinct()
                .count();

        Integer getResult = list.stream()
                .sorted()
                .findFirst()
                .orElse(0);

        // [1, 2, 2, 3, 5, 7, 9]
        System.out.println(collectResults);
        // 29
        System.out.println(reduceResult);
        // false
        System.out.println(matchResult);
        // 6
        System.out.println(countResult);
        // 1
        System.out.println(getResult);
    }

}
