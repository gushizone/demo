package tk.gushizone.java.jdk8.stream;

import com.google.common.collect.Lists;
import org.junit.Test;
import tk.gushizone.java.jdk8.entity.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-03 17:56
 */
public class OtherCaseTest {


    /**
     * 统计
     * <p>
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
    public void test6() {
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
        BigDecimal maxValue = list.stream().reduce(BigDecimal.ZERO, BigDecimal::max);
        System.out.println("最大值: " + maxValue);

        // 求最小值
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
    public void filter() {

        int size = 100000;
        List<Integer> list = Lists.newArrayListWithExpectedSize(size);
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }

        long start = System.currentTimeMillis();
        List<Integer> result = list.stream()
                .filter(e -> e == size)
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start));
    }

    @Test
    public void removeIf() {

        int size = 100000;
        List<Integer> list = Lists.newArrayListWithExpectedSize(size);
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }

        long start = System.currentTimeMillis();
        list.removeIf( e -> e != size);
        long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start));
    }

    @Test
    public void sort() {

        List<User> userList = Arrays.asList(new User("张三", 18),
                new User("李四", 32), new User("王二", 16),
                new User("李五", 26), new User("王三", 17));

        // 正序
        userList.sort(Comparator.comparing(User::getAge));
        System.out.println(userList);

        // 倒序
        userList.sort(Comparator.comparing(User::getAge).reversed());
        System.out.println(userList);

    }

    @Test
    public void distinct() {
        List<User> userList = Arrays.asList(new User("张三", 18),
                new User("李四", 32), new User("王三", 16),
                new User("李五", 26), new User("王三", 17));

        //
        List<User> sortList1 = userList.stream()
                .filter(StreamX.distinctByKey(User::getUsername))
                .collect(Collectors.toList());
        System.out.println(sortList1);

        // 去重, 但顺序改变
        List<User> sortList2 = userList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUsername))), ArrayList::new));

        System.out.println(sortList2);
    }


}
