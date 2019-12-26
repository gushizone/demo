package tk.gushizone.mybatis.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author gushizone@gmail.com
 * @date 2019-12-12 00:13
 */
@Getter
@ToString
@AllArgsConstructor
public enum  CommandEnum implements BaseCodeEnum{

    JOKE(0, "段子"),
    NEWS(1, "新闻"),
    ENTERTAINMENT(2, "娱乐"),
    MOVIE(3, "电影"),
    LOTTERY(4, "彩票"),;

    private Integer code;
    private String name;
}
