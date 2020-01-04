package tk.gushizone.web.bind.controller.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-04 21:40
 */
@Data
@XmlRootElement(name = "item")
public class XmlParam {

    private Integer id;

    private String name;
}
