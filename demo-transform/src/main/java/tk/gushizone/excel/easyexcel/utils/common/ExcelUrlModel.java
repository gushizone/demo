package tk.gushizone.excel.easyexcel.utils.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author gushizone@gmail.com
 * @date 2021/6/13 7:01 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelUrlModel<T> {


    /**
     * 文件url路径
     */
    private String fileUrl;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 表头信息
     */
    private Class<?> headClass;

    /**
     * 数据
     */
    private List<T> data;

}
