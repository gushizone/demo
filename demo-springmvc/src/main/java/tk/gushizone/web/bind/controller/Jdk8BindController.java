package tk.gushizone.web.bind.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.gushizone.web.bind.controller.dto.Object4Jdk8Dto;
import tk.gushizone.web.bind.controller.dto.Object4Jdk8Param;

import java.time.LocalDate;

/**
 * mvc 已支持 对 datetime api 的序列化和反序列化
 *
 * @author gushizone@gmail.com
 * @date 2021/2/8 4:04 下午
 */
@RestController
@RequestMapping("/mvc/bind/jdk8")
public class Jdk8BindController {

    /**
     * 绑定 datetime api - RequestParam
     * 注意 : 低版本等mvc 只支持这种方式
     * <p>GET /jdk8/mvc/bind/object?date=2021-01-03</p>
     */
    @GetMapping(value = "/localDate")
    public String localDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return String.valueOf(date);
    }

    /**
     * 绑定 datetime api - 包装对象
     * <p>GET /jdk8/mvc/bind/object?date=2021-01-03</p>
     */
    @GetMapping(value = "/object")
    public String objectGetBind(Object4Jdk8Param param) {

        return param.toString();
    }

    /**
     * 绑定 datetime api - RequestBody
     * <P>POST /jdk8/mvc/bind/object</P>
     * <pre>
     * {
     *   "id": 0,
     *   "name": "Foo",
     *   "date": "2021-01-03"
     * }
     * </pre>
     */
    @PostMapping(value = "/object")
    public Object4Jdk8Dto objectPostBind(@RequestBody Object4Jdk8Param param) {

        Object4Jdk8Dto dto = new Object4Jdk8Dto();
        BeanUtils.copyProperties(param, dto);
        return dto;
    }


}
