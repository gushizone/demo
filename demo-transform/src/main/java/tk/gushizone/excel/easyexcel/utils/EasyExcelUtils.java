package tk.gushizone.excel.easyexcel.utils;



import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import tk.gushizone.excel.easyexcel.utils.common.ExcelPropertyX;
import tk.gushizone.excel.easyexcel.utils.exception.ExcelException;
import tk.gushizone.excel.easyexcel.utils.read.SimpleAnalysisEventListener;
import tk.gushizone.excel.easyexcel.utils.write.HorizontalCellStyleStrategyFactory;
import tk.gushizone.excel.easyexcel.utils.write.converter.DateConverter;
import tk.gushizone.excel.easyexcel.utils.write.handler.ImportTempleCellWriteHandler;
import tk.gushizone.excel.easyexcel.utils.write.handler.ImportTempleRowWriteHandler;
import tk.gushizone.excel.easyexcel.utils.write.handler.ImportTempleSheetWriteHandler;
import tk.gushizone.excel.easyexcel.utils.common.ExcelHeadColumn;
import tk.gushizone.excel.easyexcel.utils.write.model.ExcelModel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gushizone@gmail.com
 * @date 2020-11-08 20:01
 */
@Slf4j
public class EasyExcelUtils {

    /**
     * head 占用行数
     */
    public static final Integer HEAD_ROW_NUMBER = 4;
    /**
     * 多选分隔符
     */
    public static final String MULTI_CHOICE_SEPARATOR = "，";
    /**
     * 统一日期格式
     */
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    /**
     * 换行符
     */
    private static final String LINE_BREAK = "\\n";

    /**
     * 批量导出
     */
    public static void write(HttpServletResponse response, ExcelModel excelModel) {

        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(getOutputStream(excelModel.getFileName(), response))
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(HorizontalCellStyleStrategyFactory.exportStyleStrategy())
                    .registerConverter(new DateConverter())
                    .useDefaultStyle(false)
                    .build();

            List<ExcelModel.Sheet<?>> sheets = excelModel.getSheets();

            for (int i = 0; i < sheets.size(); i++) {

                ExcelModel.Sheet<?> sheet = sheets.get(i);

                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheet.getSheetName())
                        .head(sheet.getHeadClass())
                        .includeColumnFiledNames(sheet.getIncludeFiledNames())
                        .build();

                excelWriter.write(sheet.getData(), writeSheet);
            }
        } catch (Exception e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("批量导出失败：{}", cause.getMessage(), cause);
            throw new ExcelException("批量导出失败：{0}", cause.getMessage());
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 下载导入模板
     */
    public static void writeTemplate(HttpServletResponse response, Class<?> headClass, String fileName) {

        try {
            EasyExcel.write(getOutputStream(fileName, response), headClass)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new ImportTempleSheetWriteHandler())
                    .registerWriteHandler(new ImportTempleRowWriteHandler())
                    .registerWriteHandler(new ImportTempleCellWriteHandler())
                    .registerConverter(new DateConverter())
                    .relativeHeadRowIndex(2)
                    .useDefaultStyle(false)
                    .sheet(0, "Sheet1")
                    .doWrite(Lists.newArrayList());
        } catch (Exception e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("下载模板失败：{}", cause.getMessage(), cause);
            throw new ExcelException("下载模板失败：{0}", cause.getMessage());
        }
    }

    /**
     * 批量导入
     */
    public static <E> List<E> read(MultipartFile file, Class<E> clazz) {

        try {
            SimpleAnalysisEventListener<E> listener = new SimpleAnalysisEventListener<>();
            EasyExcel.read(file.getInputStream(), clazz, listener)
                    .headRowNumber(HEAD_ROW_NUMBER)
                    .sheet()
                    .doRead();
            return listener.getResults();
        } catch (Exception e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("解析异常：{}", cause.getMessage(), cause);
            throw new ExcelException("解析异常：{0}", cause.getMessage());
        }

    }

    /**
     * 读取列信息
     */
    public static List<ExcelHeadColumn> readHeadColumn(Class<?> clazz) {

        try {
            Field[] fields = clazz.getDeclaredFields();
            List<ExcelHeadColumn> results = Lists.newArrayListWithExpectedSize(fields.length);
            for (Field field : fields) {
                if (!field.isAnnotationPresent(ExcelProperty.class)) {
                    continue;
                }
                ExcelHeadColumn result = new ExcelHeadColumn();

                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                String[] value = excelProperty.value();

                result.setName(value[value.length - 1]);
                result.setFieldName(field.getName());

                if (field.isAnnotationPresent(ExcelPropertyX.class)) {

                    ExcelPropertyX excelPropertyX = field.getAnnotation(ExcelPropertyX.class);

                    result.setExcelPropertyX(true);
                    result.setRequired(excelPropertyX.required());
                    result.setOptions(Arrays.asList(excelPropertyX.options()));
                    result.setMultiChoice(excelPropertyX.multiChoice());
                } else {
                    result.setExcelPropertyX(false);
                }

                results.add(result);
            }
            return results;
        } catch (Exception e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("读取列信息失败: {}", cause.getMessage(), cause);
            throw new ExcelException("读取列信息失败: {0}", cause.getMessage());
        }
    }

    public static int countLine(String remark) {
        return countLine(remark, null);
    }

    public static int countLine(String remark, Integer maxSize) {
        Matcher strMatcher = Pattern.compile(LINE_BREAK).matcher(remark);
        int count = 1;
        while (strMatcher.find()) {
            if (maxSize != null) {
                count += strMatcher.group().length() / maxSize;
            } else {
                count++;
            }
        }
        if (count == 1 && maxSize != null) {
            count += remark.length() / maxSize;
        }
        return count;
    }

    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // swagger，postman 等依然存在中文乱码，chrome 正常
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

            response.setHeader("Content-disposition", "attachment;filename=" + fileName + "-" + DateUtils.format(new Date(), DateUtils.DATE_FORMAT_14) + ExcelTypeEnum.XLSX.getValue());
            return response.getOutputStream();
        } catch (IOException e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("创建文件失败: {}", cause.getMessage(), cause);
            throw new ExcelException("创建文件失败: {0}", cause.getMessage());
        }
    }

}