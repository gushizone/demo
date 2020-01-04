package tk.gushizone.java.lombok.getterandsetter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-12-29 23:29
 */
@Setter(AccessLevel.PROTECTED)
public class Item {

    @Getter
    private Integer id;

    @Getter
    private String name;

    private String password;

}