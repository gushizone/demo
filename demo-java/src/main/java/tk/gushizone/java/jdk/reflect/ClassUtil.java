package tk.gushizone.java.jdk.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author gushizone@gmail.com
 * @date 2019-02-24 23:10
 */
public class ClassUtil {

    /**
     * 打印类的信息： 成员函数
     * @param obj
     */
    public static void printClassMethodMessage(Object obj){
        // 获取类类型
        // TODO JNI 本地方法，通过 c语言 获取
        Class clazz = obj.getClass();

        System.out.println("类的名称: " + clazz.getName());

        // Method类：方法也是对象，一个方法对应一个Method对象
        // getMethods(): 所有public方法，包括由父类继承来的
        // getDeclaredMethods(): 所有该类自己声明的方法，不论访问权限
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Class returnType = method.getReturnType();
            String methodName = method.getName();
            Class[] paramTypes = method.getParameterTypes();

            String methodInfo = returnType.getName() + " " + methodName;
            methodInfo += Arrays.stream(paramTypes).map(Class::getName).collect(Collectors.joining(", ", "(", ")"));
            System.out.println(methodInfo);
        }

    }

    /**
     * 打印类的信息： 成员变量
     * @param obj
     */
    public static void printFieldMessage(Object obj) {
        Class clazz = obj.getClass();

        // Field类: 成员变量也是对象
        // getFields(): 所有public成员变量，包括由父类继承来的
        // getDeclaredFields: 所有该类自己声明的成员变量，不论访问权限
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Class fieldType = field.getType();
            String fieldName = field.getName();

            System.out.println(fieldType + " " + fieldName + ";");
        }
    }

    /**
     * 打印类的信息： 构造函数
     */
    public static void printConstructMessage(Object obj){
        Class clazz = obj.getClass();

        // Constructor 构造方法也是函数
        // getFields(): 所有public成员变量，包括由父类继承来的
        // getDeclaredFields: 所有该类自己声明的构造函数
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor cs : constructors) {
            String csName = cs.getName();
            Class[] paramTypes = cs.getParameterTypes();

            String csInfo = csName + Arrays.stream(paramTypes).map(Class::getName).collect(Collectors.joining(", ", "(", ")"));
            System.out.println(csInfo);
        }
    }

}
