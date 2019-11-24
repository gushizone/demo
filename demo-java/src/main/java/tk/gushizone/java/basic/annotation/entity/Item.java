package tk.gushizone.java.basic.annotation.entity;

import lombok.Getter;
import lombok.Setter;
import tk.gushizone.java.basic.annotation.Description;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-11-24 21:40
 */
@Getter
@Setter
//@Description("It's Item class annotation.")
public class Item extends BaseItem {

    private String remark;

    @Override
    @Description("It's Item method annotation.")
    public String toString() {
        return "Item{" +
                "remark='" + remark + '\'' +
                '}';
    }
}
