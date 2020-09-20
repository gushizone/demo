package tk.gushizone.web.validation.validator;

import tk.gushizone.web.validation.constraints.Range;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author gushizone@gmail.com
 * @date 2020-08-01 19:17
 */
public class RangeValidator implements ConstraintValidator<Range, Object> {



    @Override
    public void initialize(Range constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }
}
