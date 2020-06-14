package tk.gushizone.java.jdk8.optional;

import org.junit.Test;

import java.util.Optional;

/**
 * Optional : 值容器, 可以简化空指针异常处理
 *
 * @author gushizone@gmail.com
 * @date 2020-05-02 16:19
 */
public class OptionalTest {

    /**
     * 构造
     */
    @Test
    public void construct() {


        Optional<Integer> opt1 = Optional.of(1);

        Optional<Integer> opt2 = Optional.ofNullable(null);
//        Optional.ofNullable(null) 等效 Optional.empty()
        Optional<Integer> opt3 = Optional.empty();
    }

    /**
     * 判断是否为空
     * <p>
     * ifPresent : 判断是否为空
     * ifPresent(Consumer) : 判断是否为空，不为空则执行
     *
     * @see java.util.function.Consumer
     */
    @Test
    public void ifPresent() {

        Optional<Integer> opt = Optional.of(1);

        // true
        System.out.println(opt.isPresent());

        // true
        opt.ifPresent(System.out::println);
    }

    /**
     * 为空处理
     * <p>
     * orElse : 为空则提供默认值
     * orElseGet(Supplier) : 为空则提供默认值
     * orElseThrow(Supplier) : 为空则抛异常
     *
     * @see java.util.function.Supplier
     */
    @Test
    public void orElse() throws Exception {

        Optional<Integer> zeroOpt = Optional.of(0);
        Optional<Integer> opt = Optional.ofNullable(null);

        // 1
        System.out.println(opt.orElse(1));
        // 0
        System.out.println(opt.orElseGet(zeroOpt::get));
        // 0
        System.out.println(zeroOpt.orElseThrow(Exception::new));
    }

    /**
     * 过滤出
     * <p>
     * filter(Predicate) : 过滤出符合条件的值，如不符合则返回Optional.empty()
     *
     * @see java.util.function.Predicate
     */
    @Test
    public void filter() {
        Optional<Integer> opt = Optional.of(0);
        // Optional.empty
        System.out.println(opt.filter(e -> e != 0));
    }

    /**
     * 映射
     * <p>
     * map(Function) : 对Optional的值运算再返回
     * flatMap(Function) : 对Optional的值运算再返回
     *
     * @see java.util.function.Function
     */
    @Test
    public void map() {
        Optional<Integer> opt = Optional.of(1);

        // Optional[100]
        System.out.println(opt.map(e -> e * 100));

        // Optional[100]
        System.out.println(opt.flatMap(e -> Optional.of(e * 100)));
    }
}
