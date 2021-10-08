package tk.gushizone.web.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gushizone@gmail.com
 * @date 2021/8/2 2:40 下午
 */
@RestController
@RequestMapping("/mvc")
public class PingController {



    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }




}
