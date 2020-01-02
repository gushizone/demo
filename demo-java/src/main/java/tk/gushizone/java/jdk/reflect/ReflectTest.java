package tk.gushizone.java.jdk.reflect;

import org.junit.Test;
import tk.gushizone.java.jdk.entity.Item;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gushizone@gmail.com
 * @date 2019-02-24 21:42
 */
public class ReflectTest {

    /**
     * 反射基础示例
     */
    @Test
    public void test() throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InstantiationException, InvocationTargetException {

        StringBuilder sb = new StringBuilder();
        sb.append("Hello world!");
        System.out.println(sb);// Hello world!

        Class clazz = Class.forName("java.lang.StringBuilder");
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("append", String.class);
        method.invoke(obj, "Hello world!");
        System.out.println(obj);// Hello world!

    }


    /**
     * Class类
     *
     * 编译时 -> 静态加载 ：new 对象是静态加载
     * 运行时 -> 动态加载
     */
    @Test
    public void test1() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Foo foo1 = new Foo();

        // 万事万物皆对象。（除了静态的成员（属于类）、普通数据类型（会有对应的封装类））
        // 任何类都是Class的实例对象，这个实例对象有三种获取方式

        // 1. 通过类获取。 可以看做：任何一个类都有一个隐含的静态成员变量class
        Class c1 = Foo.class;

        // 2.  通过对象获取。已知该类的对象，通过getClass()方法
        Class c2 = foo1.getClass();

        // 3. 通过类的全称获取 (throws ClassNotFoundException)
        Class c3 = Class.forName("tk.gushizone.java.jdk.reflect.Foo");

        // 这个实例对象(c1,c2)称之为这个类（Foo）的类类型(class type)
        // 任何类的类类型只有一个
        System.out.println(c1 == c2 && c2 == c3); // true

        // 通过一个类的类类型可以获取这个类的实例对象
        // 需要有无参数的构造方法
        Foo foo2 = (Foo) c1.newInstance();
        foo2.print(); // I'm Foo!
    }

    /**
     * Java 获取方法信息
     */
    @Test
    public void test2() {
        // 不仅是类，基本数据类型、数组、void关键字等都包含类类型
        Class c1 = Integer.class;
        Class c2 = Collection.class;

        Class c3 = ElementType.class;
        Class c4 = Override.class;
        Class c5 = int[].class;

        Class c6 = int.class;
        Class c7 = void.class;

        System.out.println(c1 == c6);// false
        System.out.println(c6 == Integer.TYPE);// true

        System.out.println(c1.getSimpleName());// Integer
        System.out.println(c2.getName());//java.util.Collection
        System.out.println(c3.getName());// java.lang.annotation.ElementType
        System.out.println(c4.getName());// java.lang.Override
        System.out.println(c5.getName());// [I
        System.out.println(c6.getName());// int
        System.out.println(c7.getName());// void

        // 获取方法信息
        ClassUtil.printClassMethodMessage(Class.class);
        //类的名称: java.lang.Class
        //void checkPackageAccess(java.lang.ClassLoader,boolean,)
        //java.lang.Class forName(java.lang.String,)
    }

    /**
     * Java 获取成员变量/构造方法信息
     */
    @Test
    public void test3() {
        // 获取成员变量信息
        ClassUtil.printFieldMessage(1);
        // int MIN_VALUE;
        // int MAX_VALUE;
        // class java.lang.Class TYPE;
        // ...

        System.out.println("======");

        // 构造方法信息
        ClassUtil.printConstructMessage("hello");
        // java.lang.String([B,int,int,)
        // java.lang.String([B,java.nio.charset.Charset,)
        // java.lang.String([B,java.lang.String,)

    }

    /**
     * 方法的反射
     */
    @Test
    public void test4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Foo foo = new Foo();
        Class clazz = Foo.class;

        // throws NoSuchMethodException
        Method method1 = clazz.getMethod("print", String.class, String.class);
        method1.invoke(foo, "abc", "def");// abc def

        Method method2 = clazz.getMethod("print", new Class[]{Integer.class, Integer.class});
        method2.invoke(foo, 12, 21);// 12 21

        Method method3 = clazz.getMethod("print");
        method3.invoke(foo);// I'm Foo!
    }

    /**
     * 通过反射认识泛型
     */
    @Test
    public void test5() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List list1 = new ArrayList();
        List<String> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();

        list1.add(1d);
        list2.add("abc");
        list3.add(1000);

        // 泛型是编译期检查错误
        // 反射运行于编译器之后

        Class c1 = list1.getClass();
        Class c2 = list2.getClass();
        Class c3 = list3.getClass();

        // 泛型不会影响类类型
        System.out.println(c1 == c2 && c2 == c3);//true

        // 反射方法作用于运行期，通过反射可以绕过泛型检查，说明编译后是去泛型化的。

        Method method = c2.getMethod("add", Object.class);

        method.invoke(list2, 100);
        method.invoke(list3, "def");

        System.out.println(list1);// [1.0]
        System.out.println(list2);// [abc, 100]
        System.out.println(list3);// [1000, def]

    }


    /**
     * 反射属性直接赋值
     */
    @Test
    public void test6() throws IllegalAccessException, InstantiationException {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "abc");

        Class clazz = Item.class;
        Object obj = clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            // 操作私有域：不进行访问域检查
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        System.out.println(obj);// Item(id=1, name=abc)
    }

    /**
     * 反射setter赋值
     */
    @Test
    public void test7() throws IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "abc");

        Class clazz = Item.class;
        Object obj = clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method wm = pd.getWriteMethod();
            wm.invoke(obj, map.get(field.getName()));
        }

        System.out.println(obj); // Item(id=1, name=abc)
    }

    /**
     * 反射构造器
     */
    @Test
    public void test8() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Item.class;
        Constructor constructor = clazz.getConstructor(new Class[]{int.class, String.class});
        Object object = constructor.newInstance(1, "abc");
        System.out.println(object); //Item(id=1, name=abc)
    }

    /**
     * instanceof & isInstance
     */
    @Test
    public void test10(){
        Object item =  new Item();
        // String str = (String) item;
        if(item instanceof Item){
            System.out.println("item instanceof Item : true");
        }

        Class clazz = Item.class;
        if(clazz.isInstance(item)){
            System.out.println("clazz.isInstance(Item) : true");
        }
    }
}
