package tk.gushizone.java.jdk.annotation.entity;

import lombok.Getter;
import lombok.Setter;
import tk.gushizone.java.jdk.annotation.orm.Column;
import tk.gushizone.java.jdk.annotation.orm.Table;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-11-24 21:39
 */
@Getter
@Setter
@Table("ITEM")
public class Filter {

    @Column("ID")
    private int id;

    @Column("NAME")
    private String name;

}
