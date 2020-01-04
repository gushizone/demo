package tk.gushizone.web.bind.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.gushizone.web.bind.controller.dto.ObjectParam;
import tk.gushizone.web.bind.controller.dto.XmlParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-04 18:33
 */
@RestController
@RequestMapping("/mvc/bind")
public class UpperBindController {

    /**
     * 绑定简单对象 / 绑定多层级对象
     * <p>GET /mvc/bind/object?id=1&name=Foo&attribute.size=M</p>
     */
    @GetMapping(value = "/object")
    public String objectGetBind(ObjectParam param) {

        return param.toString();
    }

    /**
     * 绑定简单对象 / 绑定多层级对象
     * <P>POST /mvc/bind/object</P>
     * <pre>
     * {
     *   "id": 1,
     *   "name": "Foo",
     *   "attribute": {
     *       "size": "M"
     *   }
     * }
     * </pre>
     */
    @PostMapping(value = "/object")
    public String objectPostBind(@RequestBody ObjectParam param) {

        return param.toString();
    }

    /**
     * 绑定日期类型
     * (为了最佳兼容，一般不直接使用日期类型，而使用时间戳/字符串等。)
     * <p>GET /mvc/bind/date?date=2020-01-01&localDate=2020-01-02</p>
     */
    @GetMapping(value = "/date")
    public String dateBind(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date date,
                           @RequestParam("localDate") String localDateStr) {

        LocalDate localDate = LocalDate.parse(localDateStr);

        return StringUtils.join(new Object[]{date, localDate}, " ,");
    }

    /**
     * 绑定 list
     * <p>POST /mvc/bind/list</p>
     * <pre>
     * [
     *   {
     *     "id": 1,
     *     "name": "Foo",
     *     "attribute": {
     *         "size": "M"
     *     }
     *   }
     * ]
     * </pre>
     */
    @PostMapping(value = "/list")
    public String listBind(@RequestBody List<ObjectParam> param) {

        return param.toString();
    }

    /**
     * 绑定 set
     * <p>POST /mvc/bind/set</p>
     * <pre>
     * [
     *   {
     *      "id": 1,
     *      "name": "Foo"
     *   },
     *   {
     *      "id": 1,
     *      "name": "Bar"
     *   }
     * ]
     * </pre>
     */
    @PostMapping(value = "/set")
    public String setBind(@RequestBody Set<ObjectParam> param) {

        return param.toString();
        // [ObjectParam(id=1, name=Foo, attribute=null)]
    }

    /**
     * 绑定 map
     * <p>POST /mvc/bind/map</p>
     * <pre>
     * {
     *   "size": "M",
     *   "color": "white"
     * }
     * </pre>
     */
    @PostMapping(value = "/map")
    public String mapBind(@RequestBody Map<String, String> param) {

        return param.toString();
    }

    /**
     * 绑定 xml
     * <p>POST /mvc/bind/xml</p>
     * <pre>
     * {@code
     *    <?xml version="1.0" encoding="UTF-8" ?>
     *    <item>
     *      <id>1</id>
     *      <name>Foo</name>
     *    </item>
     * }
     * </pre>
     */
    @PostMapping(value = "/xml")
    public String xmlBind(@RequestBody XmlParam param) {

        return param.toString();
    }
}
