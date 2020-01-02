package tk.gushizone.java.jdk.annotation.entity;

import tk.gushizone.java.jdk.annotation.Description;

/**
 * @author gushizone@gmail.com
 * @date 2019-03-11 14:58
 */
@Description(value = "It's BaseItem class annotation.", id = 10)
public class BaseItem {

    private int id;
    private String name;

    @Override
    @Description("It's BaseItem method annotation.")
    public String toString() {
        return "BaseItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
