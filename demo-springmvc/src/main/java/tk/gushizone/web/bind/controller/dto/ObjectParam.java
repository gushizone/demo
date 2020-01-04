package tk.gushizone.web.bind.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-04 21:40
 */
@Data
@EqualsAndHashCode(of = "id")
public class ObjectParam {

    private Integer id;

    private String name;

    private AttributeParam attribute;
}
