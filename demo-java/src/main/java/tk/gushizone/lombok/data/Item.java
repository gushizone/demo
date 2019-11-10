package tk.gushizone.lombok.data;

import lombok.Data;
import lombok.NonNull;

/**
 * @author gushizone@gmail.com
 * @date 2019-11-09 23:55
 */
@Data
public class Item {

    @NonNull
    private Integer id;

    private String name;

}
