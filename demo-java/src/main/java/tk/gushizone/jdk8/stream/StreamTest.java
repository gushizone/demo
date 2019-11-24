package tk.gushizone.jdk8.stream;

import org.junit.Test;
import tk.gushizone.jdk8.entity.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * JDK8 新特性 : Stream流
 *
 * 示例：
 * 1. 过滤空字符串：stream，filter，Collectors
 * 2. 获取对应的平方数，并去重：map，distinct
 * 3. 生成10个随机数，并排序：limit，sorted
 * 4. 统计空字符串个数：parallelStream，count
 * 5. 统计：summaryStatistics
 *
 * <p>
 * Stream（流）是一个来自数据源的元素队列并支持聚合操作。
 *
 * 用途: 可以让你以一种声明的方式处理数据，简化代码。
 *
 * 1. 元素: 特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
 * 2. 数据源: 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
 * 3. 聚合操作: 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
 *             和以前的Collection操作不同， Stream操作还有两个基础的特征：
 *
 *       Pipelining: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。
 *              这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
 *
 *       内部迭代： 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。
 *              Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。
 * </p>
 *
 * @author gushizone@gmail.com
 * @date 2019-05-14 10:55
 */
public class StreamTest {

    /**
     * 过滤空字符串。
     *
     * stream: 为集合创建串行流。
     * filter: 过滤出元素。
     * Collectors 类实现了很多归约（reduction）操作，即转化，例如将流转换成集合和聚合元素。
     */
    @Test
    public void test1() {

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abc", "", "jkl");

        List<String> filtered = strings.stream()
                .filter(string -> !string.isEmpty())
                .distinct()
                .collect(Collectors.toList());

        String mergedString = strings.parallelStream()
                .filter(string -> !string.isEmpty())
                .distinct()
                .collect(Collectors.joining(", "));
//                .collect(Collectors.joining(", ", "{", "}"));

        System.out.println(filtered);// [abc, bc, efg, jkl]
        System.out.println(mergedString);// abc, bc, efg, abc, jkl
    }


    /**
     * 获取对应的平方数，并去重。
     *
     * map: 映射每个元素到对应的结果。
     * distinct: 去重
     */
    @Test
    public void test2() {

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream()
                .map(i -> i * i)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(squaresList);// [9, 4, 49, 25]
    }

    /**
     * 生成10个随机数，并排序
     *
     * limit: 获取指定数量的流。
     * sorted: 对流进行排序。
     */
    @Test
    public void test3() {

        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);

    }


    /**
     * 统计空字符串个数
     *
     * parallelStream: 为集合创建并行流。
     * count: 统计。
     */
    @Test
    public void test4() {

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        // 获取空字符串的数量
        long count = strings.parallelStream().filter(String::isEmpty).count();

        System.out.println(count);


        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        list1.parallelStream().forEach(System.out::print);

        System.out.println("\n" + "parallelStream是并行,可能是任意的顺序, " +
                "可以使用forEachOrdered按照原来Stream的数据顺序");

        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);
        list2.parallelStream().forEachOrdered(System.out::print);

        System.out.println("\n");

        // Optional<T>
        System.out.println(list1.stream().filter(item -> item > 10).findFirst().orElse(11));


    }

    /**
     * 统计
     *
     * 另外，一些产生统计结果的收集器也非常有用。
     * 它们主要用于int、double、long等基本类型上，
     * 它们可以用来产生类似如下的统计结果。
     */
    @Test
    public void test5() {

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
    }

    /**
     * 基本类型运算
     */
    @Test
    public void test6(){
        // 求最大值 3
        List<Integer> list = Arrays.asList(1, 2, 3);
        Integer maxValue = list.stream().collect(Collectors.collectingAndThen(Collectors.maxBy((a, b) -> a - b), Optional::get));

        // 求最小值 1
        Integer minValue = list.stream().collect(Collectors.collectingAndThen(Collectors.minBy((a, b) -> a - b), Optional::get));

        // 求和 6
        Integer sumValue = list.stream().collect(Collectors.summingInt(item -> item));

        // 求平均值 2.0
        Double avg = list.stream().collect(Collectors.averagingDouble(x -> x));

    }

    /**
     * 类 SQL 操作 : 数值型
     */
    @Test
    public void test7() {

        List<BigDecimal> list = Arrays.asList(null, new BigDecimal("2.13"), new BigDecimal("1.1"), new BigDecimal("3.141"));

        // 去 null
        list = list.stream().filter(Objects::nonNull).collect(Collectors.toList());

        // 求最大值
        // BigDecimal maxValue = list.stream().collect(Collectors.collectingAndThen(Collectors.maxBy((a, b) -> a.compareTo(b)), Optional::get));
        BigDecimal maxValue = list.stream().reduce(BigDecimal.ZERO, BigDecimal::max);
        System.out.println("最大值: " + maxValue);

        // 求最小值
        // BigDecimal minValue = list.stream().collect(Collectors.collectingAndThen(Collectors.minBy(BigDecimal::compareTo), Optional::get));
        BigDecimal minValue = list.stream().reduce(BigDecimal.ZERO, BigDecimal::min);
        System.out.println("最小值: " + minValue);

        // 求和（其他运算类似）
        BigDecimal sumValue = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("和: " + sumValue);

        // 求平均值 （四舍五入，保留小数位）
        BigDecimal avg = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(list.size()), 6, RoundingMode.HALF_UP);
        System.out.println("平均值: " + avg);

        // 升序
        List<BigDecimal> sortList = list.stream().sorted().collect(Collectors.toList());
        System.out.println("ASC: " + sortList);
        // 降序
        List<BigDecimal> revList = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("DESC: " + revList);

    }

    /**
     * 类 SQL 操作 : 复杂对象
     */
    @Test
    public void test8() {
        List<User> userList = Arrays.asList(new User("张三", 18),
                new User("李四", 32), new User("王二", 16),
                new User("李五", 26), new User("王三", 17));

        // 分组 : 根据姓氏
        Map<String, List<User>> map1 = userList.stream().collect(Collectors.groupingBy(item -> item.getUsername().substring(0, 1)));
        System.out.println(map1);

        // 分组 : 根据是否成年
        Map<Boolean, List<User>> map2 = userList.stream().collect(Collectors.groupingBy(item -> item.getAge() > 18));
        System.out.println(map2);

        // 分组 : 计数，根据是否成年
        Map<Boolean, Long> map3 = userList.stream().collect(Collectors.groupingBy(item -> item.getAge() > 18, Collectors.counting()));
        System.out.println(map3);

        // 分组 : 取出同姓中最年轻的人
        Map<String, User> map4 = userList.stream().collect(Collectors.groupingBy(item -> item.getUsername().substring(0, 1),
                Collectors.collectingAndThen(Collectors.toList(), list -> list.stream()
                        .sorted(Comparator.comparingInt(User::getAge)).collect(Collectors.toList()).get(0))));
        System.out.println(map4);
    }

    @Test
    public void test100() {

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abc", "", "jkl");

        for (String item : strings) {
            System.out.println(item.hashCode());
        }

        System.out.println("====");

        List<String> strings00 = strings.stream().filter("abc"::equals).collect(Collectors.toList());
        for (String item : strings00) {
            System.out.println(item.hashCode());
        }

    }


}
