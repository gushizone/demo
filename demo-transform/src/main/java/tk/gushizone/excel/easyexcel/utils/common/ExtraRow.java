package tk.gushizone.excel.easyexcel.utils.common;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Data;

/**
 * @author gushizone@gmail.com
 * @date 2021/6/12 7:50 下午
 */
@Data
public class ExtraRow {

    /**
     * 数据行号 - excel真实行号，从1开始
     */
    @ExcelIgnore
    private Integer index;

}
