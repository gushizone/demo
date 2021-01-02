package tk.gushizone.excel.easyexcel.utils.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelHeadColumn {

    /**
     * 列索引（excel中的真实索引位，从 0 开始。）
     */
    private Integer index;

    /**
     * 列名称
     */
    private String name;

    /**
     * 对应的字段名
     */
    private String fieldName;

    /**
     * 是否使用了excelPropertyX注解
     */
    private Boolean excelPropertyX = false;

    /**
     * 是否必填
     */
    private Boolean required = false;

    /**
     * 可选项
     */
    private List<String> options;

    /**
     * 是否多选
     */
    private Boolean multiChoice = false;
}
