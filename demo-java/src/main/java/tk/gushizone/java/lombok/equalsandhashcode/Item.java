package tk.gushizone.java.lombok.equalsandhashcode;

import lombok.EqualsAndHashCode;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-12-29 23:29
 */
@EqualsAndHashCode(of="id")
public class Item {

    private Integer id;

    private String name;

    private String password;

}