package tk.gushizone.java.jdk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用@interface关键字定义注解
 *
 * 成员类型是受限制的，仅限: 基本类型（不允许包装类型）和String，Class，Annotation，Enumeration。
 *
 * 约定俗成的规范：
 * 如果只有一个成员，则需要取名为value(), 这样在使用时可以忽略成员名和赋值号（=）。
 * 可以用其他命名，默认赋值只能作用于value()
 *
 * 注解类可以没有成员，没有成员的注解称为标识注解。
 *
 *
 * <p>元注解 : 作用于注解的注解。</p>
 *
 * @see Target : 作用域。ElementType
 * @see Retention : 声明周期。RetentionPolicy
 * @see Inherited : 允许子类继承注解 (只会继承类级别，不会继承方法级别) // 标识注解
 * @see Documented : 生成 javadoc 时会包含注解信息 // 标识注解
 *
 * @author gushizone@gmail.com
 * @date 2019-11-24 21:29
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Description {

    /**
     * 成员以无参无异常方式声明
     */
    String value();

    /**
     * 可以用default指定默认值
     */
    int id() default 100;
}
