package tk.gushizone.rabbitmq.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gushizone@gmail.com
 * @date 2020-03-17 22:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

    /**
     * 一定要添加，否则默认serialVersionUID改变会导致消息反序列化失败，造成消息堵塞
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

}
