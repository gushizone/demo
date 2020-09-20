package tk.gushizone.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * controller 统一异常处理
 *
 *
 * @author gushizone@gmail.com
 * @date 2020-06-20 15:36
 */
@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

    @Resource
    private HttpServletRequest httpServletRequest;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {

        log.warn("系统异常: {}", ex.getMessage());
        return ex.getMessage();
    }


    /**
     * @see MethodArgumentNotValidException 需要 @Valid/@Validated + @RequestBody
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleBindException(MethodArgumentNotValidException ex) {

        FieldError fieldError = ex.getBindingResult().getFieldError();
        log.warn("MethodArgumentNotValidException: {}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return fieldError.getDefaultMessage();
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex) {

        FieldError fieldError = ex.getBindingResult().getFieldError();
        log.warn("BindException: {}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return fieldError.getDefaultMessage();
    }


}
