/**
 * Copyright (c) https://github.com/gushizone
 */
package tk.gushizone.web.validation.controller;

import com.google.common.collect.Maps;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.gushizone.web.validation.controller.dto.ValidParam;
import tk.gushizone.web.validation.controller.dto.ValidatedParam;
import tk.gushizone.web.validation.controller.dto.ValidatorParam;
import tk.gushizone.web.validation.groups.DeleteValidateGroup;

import javax.validation.Valid;
import java.util.Map;

/**
 * 数据校验
 * @author gushizone@gmail.com
 * @date 2019-08-09 16:03
 */
@RestController
@RequestMapping("/mvc/validation")
public class ValidationController {

    private Map<String, String> fieldErrors(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return Maps.newHashMap();
        }
        Map<String, String> map = Maps.newHashMapWithExpectedSize(bindingResult.getFieldErrors().size());
        for (FieldError error : bindingResult.getFieldErrors()) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return map;
    }

    /**
     * POST /mvc/validation/valid
     */
    @PostMapping("/valid")
    public String valid(@Valid @RequestBody ValidParam param) {
        return "OK";
    }

    /**
     * GET /mvc/validation/valid?name=Foo
     */
    @GetMapping("/valid")
    public String valid(@Valid ValidParam param, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return fieldErrors(bindingResult).toString();
            // {id=id不能为空！}
        }

        return "OK";
    }

    /**
     * DELETE /mvc/validation/validated
     */
    @DeleteMapping("/validated")
    public String validated(@Validated(DeleteValidateGroup.class) ValidatedParam param, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return fieldErrors(bindingResult).toString();
            // {id=id不能为空！}
        }

        return "OK";
    }

    /**
     * GET /mvc/validation/binding-result?name=admin
     */
    @GetMapping("/binding-result")
    public String bindingResult(ValidatedParam param, BindingResult bindingResult) {

        if ("admin".equals(param.getName())) {
            bindingResult.rejectValue("name", "nonname", "此名称不允许使用！");
        }

        if (bindingResult.hasErrors()){
            return fieldErrors(bindingResult).toString();
            // {name=此名称不允许使用！}
        }

        return "OK";
    }


    /**
     * GET /mvc/validation/validator?name=foo
     */
    @GetMapping("/validator")
    public String validator(@Validated ValidatorParam param, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return fieldErrors(bindingResult).toString();
            // {name=lengthCheck不通过！}
        }

        return "OK";
    }

}
