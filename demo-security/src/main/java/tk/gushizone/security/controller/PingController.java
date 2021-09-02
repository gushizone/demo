package tk.gushizone.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gushizone@gmail.com
 * @date 2021/8/17 5:34 下午
 */
@RestController
@RequestMapping("/security/ping")
public class PingController {

    @GetMapping
    public String ping() {
        return "pong";
    }

}
