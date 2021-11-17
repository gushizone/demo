package tk.gushizone.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gushizone@gmail.com
 * @date 2020-03-17 22:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Integer id;
    private String name;

}
