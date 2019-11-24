package tk.gushizone.java.lombok.builder;

/**
 * @author gushizone@gmail.com
 * @date 2019-11-10 21:02
 */
public class BaseItem {

    private Integer id;

    public BaseItem() {
    }

    public BaseItem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}