package tk.gushizone.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gushizone
 * @date 2022/5/19 12:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> implements Serializable {

    private String id;

    private T body;


}
