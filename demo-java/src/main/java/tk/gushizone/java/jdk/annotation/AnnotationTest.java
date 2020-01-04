package tk.gushizone.java.jdk.annotation;

import org.junit.Test;
import tk.gushizone.java.jdk.annotation.entity.Item;
import tk.gushizone.java.jdk.annotation.entity.Filter;
import tk.gushizone.java.jdk.annotation.orm.ORM;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *
 * 注解分类 ：生命周期
 *
 * 1. 源码注解 : 注解只在源码中存在，编译成.class文件就不存在了。
 * 2. 编译时注解 : 注解在源码和.class文件中都存在。（上面的JDK注解都属于该类型）
 * 3. 运行时注解 : 注解在运行阶段还会起作用，甚至会影响运行逻辑。（例如@Autowired等，多见于第三方注解）
 *
 * 注解分类 ： 来源
 * 1.JDK自带
 * 2.来自第三方的注解
 * 3.自定义的注解
 *
 * JDK注解
 * @see Override : 覆盖，覆盖来自超类型（父类或接口）的方法。
 * @see Deprecated : 标识方法过时，使用处编译器会显示警告信息。
 * @see SuppressWarnings : 抑制编译器警告，需要制定警告集。@SuppressWarnings("unchecked") 抑制未检查的警告。
 *
 * @author gushizone@gmail.com
 * @date 2019-03-11 11:22
 */
public class AnnotationTest {

    /**
     * 解析注解
     */
    @Test
    public void test1(){
        Class clazz = Item.class;
        boolean isExist = clazz.isAnnotationPresent(Description.class);
        if (isExist){
            Description d1 = (Description) clazz.getAnnotation(Description.class);

            System.out.println(d1.value());
        }

        Method[] methods = clazz.getDeclaredMethods();
        // 方式一： 推荐
        for (Method method : methods) {
            boolean isMExist = method.isAnnotationPresent(Description.class);
            if (isMExist){
                Description d2 = method.getAnnotation(Description.class);

                System.out.println(method.getName() + ":" + d2.value());
            }
        }

        // 方式二
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Description){
//                if (Description.class.isInstance(annotation)){
                    Description d3 =  method.getAnnotation(Description.class);

                    System.out.println(method.getName() + ":" + d3.value());
                }
            }
        }

//        It's BaseItem class annotation.
//        toString:It's Item method annotation.
//        toString:It's Item method annotation.
    }

    /**
     * 利用 注解 实现简单的orm
     */
    @Test
    public void test2() throws Exception {
        Filter filter = new Filter();
        filter.setId(1);
        filter.setName("foo");

        System.out.println(ORM.query(filter));
        // SELECT ID,NAME FROM ITEM WHERE  ID=1 AND NAME='foo'

    }

}
