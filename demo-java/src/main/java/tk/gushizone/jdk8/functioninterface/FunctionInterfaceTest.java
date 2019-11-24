package tk.gushizone.jdk8.functioninterface;

import org.junit.Test;

/**
 * JDK8 新特性 : 函数式接口
 *
 * <p>
 * 函数式接口(Functional Interface)就是一个有且仅有一个抽象方法（Object的public方法除外）。
 *
 * 用途: 函数式接口可以被隐式转换为lambda表达式，与 lambda 配合使用。
 *
 * JDK 1.8 新增加的函数接口：
 * java.util.function包 ★（定义了大量常用的函数式接口，可以直接使用）
 * 	  Function<T,R> ： 接受一个输入参数，返回一个结果。
 *    Supplier<T> ： 无参数，返回一个结果。
 *    Consumer<T> ： 接受一个输入参数，无返回的操作。
 *
 * JDK 1.8之前已有的函数式接口:
 * @see java.lang.Runnable
 * @see java.util.concurrent.Callable
 * @see java.security.PrivilegedAction
 * @see java.util.Comparator
 * @see java.io.FileFilter
 * @see java.nio.file.PathMatcher
 * @see java.lang.reflect.InvocationHandler
 * @see java.beans.PropertyChangeListener
 * @see java.awt.event.ActionListener
 * @see javax.swing.event.ChangeListener
 * </p>
 *
 * @author gushizone@gmail.com
 * @date 2019-11-10 23:44
 */
public class FunctionInterfaceTest {

    /**
     * 自定义类Function函数式接口
     * 数组构造引用
     */
    @Test
    public void test1(){

        FunctionInterfaceDemo<Integer, int[]> fid1 = (i) -> new int[i];
        int[] arr1 = fid1.get(5);
        System.out.println(arr1 + ":" + arr1.length);

//        数组构造引用
        FunctionInterfaceDemo<Integer, int[]> fid2 = int[]::new;
        int[] arr2 = fid2.get(5);
        System.out.println(arr2 + ":" + arr2.length);

    }



}
