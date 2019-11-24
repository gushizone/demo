package tk.gushizone.java.jdk8.defaultmethod;

import org.junit.Test;

/**
 * JDK8 新特性 : 默认方法
 *
 * <p>示例：默认方法 和 静态方法</p>
 *
 * @author gushizone@gmail.com
 * @date 2019-11-10 23:39
 */
public class DefaultMethodTest {


    /**
     * 简单说，默认方法就是接口可以有实现方法，而且不需要实现类去实现其方法。
     * (🤣接口和抽象类都可以有实现方法了)
     * <p>
     * 集合框架的forEach()方法就是很好的例子。
     * <p>
     * 用途：解决接口的修改与现有的实现不兼容的问题。（
     * jdk8 以前，接口是个双刃剑，
     * 好处是面向抽象而不是面向具体编程，
     * 缺陷是当需要修改接口时候，需要修改全部实现该接口的类。
     * ）
     */
    @Test
    public void defaultMethod() {

        new Car().print();
    }


}
