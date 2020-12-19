package tk.gushizone.web.bind.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-04 18:33
 */
@RestController
@RequestMapping("/mvc/bind")
public class LowerBindController {

    /**
     * 绑定基本类型(不推荐 )
     * <p>
     * GET /mvc/bind/primitive?id=1
     */
    @GetMapping(value = "/primitive")
    public String primitiveBind(int id) {

        return "id: " + id;
    }

    /**
     * 绑定包装类型
     * <p>
     * GET /mvc/bind/boxed?id=1
     */
    @GetMapping(value = "/boxed")
    public String boxedBind(@RequestParam("id") Integer id) {

        return "id: " + id;
    }

    /**
     * 绑定包装类型
     * <p>
     * GET /mvc/bind/boxed/1
     */
    @GetMapping(value = "/boxed/{id}")
    public String restBind(@PathVariable("id") Integer id) {

        return "id: " + id;
    }

    /**
     * 绑定数组类型(不推荐)
     * get传递数组较困难，一般会传string再json解析。
     * <p>
     * GET http://localhost:8080/mvc/bind/array?name=foo&name=bar&codes=foo,bar
     */
    @GetMapping(value = "/array")
    public String arrayBind(@RequestParam("name") String[] names,
                            @RequestParam("codes") String[] codes) {
        return Arrays.toString(names) + " | "
                + Arrays.toString(codes);
    }
}
