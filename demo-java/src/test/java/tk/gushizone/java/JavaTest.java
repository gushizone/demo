package tk.gushizone.java;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-08-23 18:41
 */
public class JavaTest {

    @Test
    public void test() throws InterruptedException {


        System.out.println(13&17);

        // 时间戳以1970年1月1日为起始，之所以多8小时，是因为当前在东八区
        // Thu Jan 01 08:00:00 CST 1970
        System.out.println(new Date(0));
    }


    /**
     * 由于泛型擦除,使得Generic无法获取自己的Generic的Type类型。
     * 实际上BadClass()实例化以后Class里面就不包括T的信息了，对于Class而言T已经被擦拭为Object，
     * 而真正的T参数被转到使用T的方法（或者变量声明或者其它使用T的地方）里面（如果没有那就没有存根），
     * 所以无法反射到T的具体类别，也就无法得到T.class。
     * 而getGenericSuperclass()是Generic继承的特例，对于这种情况子类会保存父类的Generic参数类型，
     * 返回一个ParameterizedType，这时可以获取到父类的T.class了，这也正是子类确定应该继承什么T的方法
     */
    @Test
    public void test1() {

        List<String> list = Lists.newArrayList();
        System.out.println(list.getClass().getGenericSuperclass());
        // java.util.AbstractList<E>

        DemoList demoList = new DemoList();
        System.out.println(demoList.getClass().getGenericSuperclass());
        // java.util.ArrayList<java.lang.Integer>

    }

    public static class DemoList extends ArrayList<Integer> {}




}
