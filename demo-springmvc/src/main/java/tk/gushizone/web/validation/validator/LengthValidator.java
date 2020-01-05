package tk.gushizone.web.validation.validator;

import tk.gushizone.web.validation.constraints.Length;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author gushizone@gmail.com
 * @date 2019-08-05 23:38
 */
public class LengthValidator implements ConstraintValidator<Length, Object> {

    /**
     * 自定义message
     */
    private String message;

    /**
     * 最小值
     */
    private int min;

    /**
     * 最大值
     */
    private int max;

    /**
     * 实际长度
     */
    private int length;

    @Override
    public void initialize(Length constraintAnnotation) {
        int max = constraintAnnotation.max();
        int min = constraintAnnotation.min();
        int length = constraintAnnotation.length();
        if (min > 0) {
            this.min = min;
        }
        if (max > 0) {
            this.max = max;
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int strLength = value.toString().length();

        return strLength >= min && strLength <= max;
    }
}
