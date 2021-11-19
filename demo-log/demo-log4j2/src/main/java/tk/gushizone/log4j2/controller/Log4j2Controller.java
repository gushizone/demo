package tk.gushizone.log4j2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/18 2:12 下午
 */
@Slf4j
@RestController
@RequestMapping("/log4j2")
public class Log4j2Controller {

    @GetMapping("/log")
    public String log() {

        log.info("info 日志...");
        log.warn("warn 日志...");
        log.error("error 日志...");

        return "log";
    }

    @GetMapping("/error")
    public String error() {

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("error 日志...: {}", e.getMessage(), e);
        }
        return "error";
    }

}
