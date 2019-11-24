package tk.gushizone.java.jdk8.lambda;

import org.junit.Test;


/**
 * JDK8 新特性 : Lambda表达式 （闭包 / 匿名方法）
 *
 * 示例：
 *  1. 带返回值的lambda表达式：四则运算
 *  2. 不带返回值的lambda表达式：打印
 *  3. 注意点： lambda表达式变量作用域
 *
 *  <p>
 * Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
 *
 * 用途: Lambda 表达式主要用来定义行内执行的 函数式接口（只有一个抽象方法）。
 *
 * lambda 表达式的语法格式：
 * (parameters) -> expression 或 (parameters) ->{ statements; }
 *
 * lambda表达式的重要特征:
 *  1. 可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。
 *  2. 可选的参数圆括号：一个参数无需定义圆括号，但没有参数或多个参数需要定义圆括号。
 *  3. 可选的大括号：如果主体包含了一个语句，就不需要使用大括号。
 *  4. 可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。
 *  </p>
 *
 * @author gushizone@gmail.com
 * @date 2019-11-13 10:56
 */
public class LambdaTest {

    /**
     * 带返回值的lambda表达式：四则运算
     *
     * 函数式接口可以接收 lambda 表达式
     */
    @Test
    public void test1(){
        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;
        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;
        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> { return a * b; };
        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + LambdaTest.operate(10, 5, addition));
        System.out.println("10 - 5 = " + LambdaTest.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + LambdaTest.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + LambdaTest.operate(10, 5, division));

    }

    /**
     * 变量作用域
     * 1. lambda 表达式的局部变量可以不用声明为 final，但是但初始化后就不允许修改（即隐性的具有 final 的语义）。
     * 2. 在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量。
     */
    @Test
    public void test2(){

        /**
         * lambda 表达式的局部变量具有 隐性final
         */
        int num = 1;
        Converter<Integer, String> s = (param) -> System.out.println(String.valueOf(param + num));
        s.convert(2);
//        num = 5; // ERROR : Local variable num defined in an enclosing scope must be final or effectively final


        String first = "";
//        ERROR : Variable 'first' is already defined in the scope
//        Comparator<String> comparator = (first, second) -> Integer.compare(first.length(), second.length());
    }


    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private static int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

    public interface Converter<T1, T2> {
        void convert(int i);
    }



}
