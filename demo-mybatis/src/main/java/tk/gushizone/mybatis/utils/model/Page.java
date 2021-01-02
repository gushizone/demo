package tk.gushizone.mybatis.utils.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:33
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

    /**
     * 结果集
     */
    private List<T> list;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页的数量
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int pages;

    public static <T> Page<T> empty() {
        return Page.<T>builder()
                .list(Collections.emptyList())
                .total(NumberUtils.LONG_ZERO)
                .pageNum(NumberUtils.INTEGER_ZERO)
                .pageSize(NumberUtils.INTEGER_ZERO)
                .pages(NumberUtils.INTEGER_ZERO)
                .build();
    }
}