package tk.gushizone.java.lombok.tostring;

import lombok.ToString;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-12-29 23:20
 */
@ToString(exclude = "password")
public class Item {
    
    private Integer id;
    
    private String name;

    private String password;

}