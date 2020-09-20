package tk.gushizone.web.validation;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tk.gushizone.web.validation.controller.dto.ValidatedParam;
import tk.gushizone.web.validation.controller.dto.ValidatorParam;
import tk.gushizone.web.validation.groups.EditValidateGroup;
import tk.gushizone.web.validation.util.ValidationUtils;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-02-03 10:51
 */
@Slf4j
public class ValidationTest {

    @Test
    public void test() {
        List<String> errorMessages = ValidationUtils.getFieldErrorMessages(new ValidatedParam(), EditValidateGroup.class);
        log.warn("errorMessages : {}", errorMessages);
    }

    @Test
    public void testLength() {
//        ValidatorParam


        List<String> errorMessages = ValidationUtils.getFieldErrorMessages(new ValidatorParam(1, "a"), null);
        System.out.println(errorMessages);



    }
}
