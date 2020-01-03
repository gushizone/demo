package tk.gushizone.json.jackson.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author gushizone@gmail.com
 * @date 2020-01-02 18:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Integer id;

    private String name;

    private BigDecimal price;

    private Date createTime;

}
