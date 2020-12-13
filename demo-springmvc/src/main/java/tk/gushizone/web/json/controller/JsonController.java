package tk.gushizone.web.json.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.gushizone.web.json.dto.JsonParam;

/**
 * @author gushizone@gmail.com
 * @date 2020-12-13 21:54
 */
@Slf4j
@RestController
@RequestMapping("/mvc/json")
public class JsonController {


    @PostMapping("test")
    public JsonParam test(@RequestBody JsonParam param) {

        log.info(param.toString());

        return param;
    }


}
