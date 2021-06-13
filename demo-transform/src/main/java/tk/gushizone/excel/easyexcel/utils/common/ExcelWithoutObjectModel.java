package tk.gushizone.excel.easyexcel.utils.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2021/6/13 7:18 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelWithoutObjectModel {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 包含字段名称
     */
    private List<String> includeFiledNames;

    /**
     * 表头
     */
    private List<List<String>> head;

    /**
     * 数据
     */
    private List<List<?>> data;

}
