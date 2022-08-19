package tk.gushizone.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author gushizone
 * @date 2022/5/19 12:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

    private Long id;

    private List<String> address;

}
