package tk.gushizone.web.validation.constraints;


import tk.gushizone.web.validation.validator.LengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-05 23:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = {LengthValidator.class})
public @interface Length {

    String fieldName() default "";

    int min() default 0;

    int max() default 20;

    int length() default 0;

    /**
     * 校验提示信息
     */
    String message() default "lengthCheck不通过";

    /**
     * 校验组
     */
    Class<?>[] groups() default {};

    /**
     * 校验错误级别
     */
    Class<? extends Payload>[] payload() default {};
}
