package tk.gushizone.java.jdk8.methodreference;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JDK8 新特性 : 方法引用
 * 示例：
 * 1. 经典的方法引用
 * 2. 四种方法引用类型
 * 3. 数组引用
 *
 * <p>
 * 方法引用 可以理解为Lambda表达式的另一种表现形式（缩写）
 *
 * 用途：当要传递给Lambda体内的操作，已经有实现的方法了，就可以使用方法引用了。
 *      推荐使用方法引用，替代 lambda 表达式 ☆，这样代码更加简洁清晰。
 *
 * 方法引用的使用条件:
 *  1.方法引用所引用的方法的参数列表必须要和函数式接口中抽象方法的 参数列表 相同（完全一致）
 *  2.方法引用所引用的方法的的返回值必须要和函数式接口中抽象方法的 返回值 相同（完全一致）
 * </p>
 *
 * @author gushizone@gmail.com
 * @date 2019-11-10 23:48
 */
public class MethodReferenceTest {

    /**
     * 经典的方法引用示例
     * 1. forEach 方法 是1.8集合框架可使用的遍历方法（实现了Iterable接口）
     * <p>
     *  default void forEach(Consumer<? super T> action) {
     *    Objects.requireNonNull(action);
     *      for (T t : this) {
     *        action.accept(t);
     *      }
     *  }
     * </p>
     * 2. Consumer是 函数式接口 , 具有一个抽象方法
     * void accept(T t);
     * 3. System.out.println(Object x)方法 和 accept(T t) 是 相同参数 相同返回值 的。
     * 梳理 : forEach方法 会遍历并执行一个操作逻辑，即 其所接受的参数（Lambda / 方法引用）。
     */
    @Test
    public void test1() {
        List<String> names = new ArrayList<>();

        names.add("Google");
        names.add("Runoob");

        names.forEach(System.out::println);
//        names.forEach(item -> System.out.println(item)); // 等同于以上的函数引用

    }


    /**
     * 四种方法引用类型
     * 1. 构造器方法引用(调用默认构造器) : Class::new
     * 2. 类静态方法引用 : Class::static_method
     * 3. 类普通方法引用(被调用方法不能具有参数) : Class::method
     * 4. 实例方法引用 : instance::method
     */
    @Test
    public void test2() {

//        1. 构造器方法引用(调用默认构造器) : Class::new，或 Class<T>::new
        Car car1 = Car.create(Car::new);
        Car car2 = Car.create(Car::new);
        Car car3 = Car.create(Car::new);
        List<Car> cars = Arrays.asList(car1, car2, car3);

//        2. 类静态方法引用 : Class::static_method
        cars.forEach(Car::collide);

//        3. 类普通方法引用(被调用方法不能具有参数) : Class::method
        cars.forEach(Car::repair);

//        4. 实例方法引用 : instance::method
        Car police = Car.create(Car::new);
        cars.forEach(police::follow);

    }


}
