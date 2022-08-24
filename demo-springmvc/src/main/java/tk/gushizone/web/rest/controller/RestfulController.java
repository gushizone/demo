package tk.gushizone.web.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gushizone
 * @date 2022/8/23 16:16
 */
@Slf4j
@RestController
@RequestMapping("/rest")
public class RestfulController {

    @GetMapping("/books")
    public String list() {
        return "OK";
    }


    @GetMapping("/books/{id}")
    public String detail(@PathVariable String id) {
        return "OK";
    }

    @PostMapping("/books")
    public String create() {
        return "OK";
    }

    @PutMapping("/books/{id}")
    public String update(@PathVariable String id) {
        return "OK";
    }

    @DeleteMapping("/books/{id}")
    public String delete(@PathVariable String id) {
        return "OK";
    }



    @PostMapping("/books/{id}:undelete")
    public String undelete(@PathVariable String id) {
        return "OK";
    }

    @PostMapping("/books:batch-export")
    public String batchExport(@RequestBody List<Integer> ids) {
        return "OK";
    }

}
