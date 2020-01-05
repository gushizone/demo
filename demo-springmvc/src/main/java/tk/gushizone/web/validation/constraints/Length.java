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

    String message() default "lengthCheck不通过！";


    /**
     * FIXME : 一定要添加 groups 和 payload
     * <p>
     * javax.validation.ConstraintDefinitionException: HV000074
     * Length contains Constraint annotation, but does not contain a groups parameter.
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
