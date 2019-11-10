package tk.gushizone.lombok.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gushizone@gmail.com
 * @date 2019-11-09 23:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Integer id;

    private String name;

}
