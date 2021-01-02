package tk.gushizone.excel.easyexcel.utils.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:07
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelPropertyX {

    /**
     * 是否必填
     */
    boolean required() default false;

    /**
     * 可选项（默认为批注）
     */
    String[] options() default {};

    /**
     * 是否多选
     */
    boolean multiChoice() default false;

    /**
     * 批注
     */
    String note() default "";
}
