package tk.gushizone.web.validation.util;

import com.google.common.collect.Lists;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

/**
 *
 * @author gushizone@gmail.com
 * @date 2020-02-11 16:32
 */
public class ValidationUtils {

    public static Validator factory(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    public static <T> List<String> getFieldErrorMessages(T object, Class<?>... groups) {
        List<String> messages = Lists.newArrayList();

        Validator validator = factory();
        Set<ConstraintViolation<T>> validate = validator.validate(object, groups);
        validate.forEach( e -> messages.add(e.getMessageTemplate().equals(e.getMessage()) ? e.getMessage() : e.getPropertyPath() + e.getMessage()));
        return messages;
    }
}