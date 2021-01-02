package tk.gushizone.excel.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.gushizone.excel.easyexcel.utils.common.ExcelPropertyX;

import java.util.Date;

/**
 * @author gushizone@gmail.com
 * @date 2020-11-08 20:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @ExcelProperty(index = 0, value = "序号")
    private Integer id;

    @ExcelPropertyX(required = true)
    @ExcelProperty(index = 1, value = "名称")
    private String name;

    @ExcelProperty(index = 2, value = "创建时间")
    private Date createTime;

}
