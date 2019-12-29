package tk.gushizone.java.lombok.builder;

import lombok.Builder;

/**
 * 当 pojo 继承基类时，@Builder需要作用于全参构造器上，才能构造父类属性。
 *
 * @author gushizone@gmail.com
 * @date 2019-11-10 21:18
 */
public class Item extends BaseItem {

    private String name;

    @Builder
    public Item(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
