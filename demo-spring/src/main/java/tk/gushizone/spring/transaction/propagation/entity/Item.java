package tk.gushizone.spring.transaction.propagation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 20:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    public static final Item SAMPLE = new Item(1L, "Foo", "remark");

    private Long id;

    private String name;

    private String remark;
}