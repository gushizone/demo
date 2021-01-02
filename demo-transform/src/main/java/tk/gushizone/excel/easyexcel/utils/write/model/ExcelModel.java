package tk.gushizone.excel.easyexcel.utils.write.model;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-11-08 20:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelModel {

    /**
     * 文件名称
     */
    private String fileName;


    /**
     * sheet参数
     */
    private List<Sheet<?>> sheets;

    public static ExcelModel build(String fileName, Sheet<?> sheet) {
        return ExcelModel.builder()
                .fileName(fileName)
                .sheets(Lists.newArrayList(sheet))
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sheet<T> {

        /**
         * sheet名
         */
        private String sheetName;

        /**
         * 表头信息
         */
        private Class<T> headClass;

        /**
         * 数据
         */
        private List<T> data;

        /**
         * 只包含的列
         */
        private List<String> includeFiledNames;
    }

}