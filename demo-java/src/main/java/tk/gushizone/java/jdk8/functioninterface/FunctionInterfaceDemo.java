package tk.gushizone.java.jdk8.functioninterface;


/**
 *
 * 函数式接口示例
 * 该接口类似：Function
 *
 * @see FunctionalInterface jdk8 新增，用于函数式接口的编译级错误检查（非必须）。
 *
 * @author gushizone@gmail.com
 * @date 2019-11-10 23:42
 */
@FunctionalInterface
public interface FunctionInterfaceDemo<T, R> {

    R get(T t);

    /**
     * 函数式接口，有且仅有一个抽象方法，Object的public方法除外。     *
     * @param obj
     * @return
     */
    @Override
    boolean equals(Object obj);


}
