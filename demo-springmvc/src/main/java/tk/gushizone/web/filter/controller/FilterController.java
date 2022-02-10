package tk.gushizone.web.filter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gushizone@gmail.com
 * @date 2022/2/10 10:37 上午
 */
@Slf4j
@RestController
@RequestMapping("/filter")
public class FilterController {


    @GetMapping
    public String test() {

//        int i = 1 / 0;
        log.info("======== do something...");
//        int j = 1 / 0;
        return "ok";
    }

}
