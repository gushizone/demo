package tk.gushizone.excel.easyexcel.utils;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.gushizone.excel.easyexcel.utils.common.ExcelHeadColumn;
import tk.gushizone.excel.easyexcel.utils.common.ExcelPropertyX;
import tk.gushizone.excel.easyexcel.utils.common.ExcelUrlModel;
import tk.gushizone.excel.easyexcel.utils.common.ExcelWithoutObjectModel;
import tk.gushizone.excel.easyexcel.utils.common.ExtraRow;
import tk.gushizone.excel.easyexcel.utils.exception.ExcelException;
import tk.gushizone.excel.easyexcel.utils.read.SimpleAnalysisEventListener;
import tk.gushizone.excel.easyexcel.utils.write.HorizontalCellStyleStrategyFactory;
import tk.gushizone.excel.easyexcel.utils.write.converter.DateConverter;
import tk.gushizone.excel.easyexcel.utils.write.handler.ImportTempleCellWriteHandler;
import tk.gushizone.excel.easyexcel.utils.write.handler.ImportTempleRowWriteHandler;
import tk.gushizone.excel.easyexcel.utils.write.handler.ImportTempleSheetWriteHandler;
import tk.gushizone.excel.easyexcel.utils.write.model.ExcelModel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author gushizone@gmail.com
 * @date 2020-11-08 20:01
 */
@Slf4j
public class EasyExcelUtils {

    /**
     * head 开始位 - 用于确定从哪行开始写
     */
    public static final Integer RELATIVE_HEAD_ROW_INDEX = 2;

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
    private static final String LINE_BREAK_REGX = "\\n";
    /**
     * 数字
     */
    public static final String NUMBER_REGX = "\\d+";


    /* -----------------------------------  批量导出  ------------------------------------------------ */

    /**
     * 批量导出
     */
    public static void write(HttpServletResponse response, ExcelModel excelModel) {

        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(outputStream(excelModel.getFileName(), response))
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
     * 批量导出 - 根据固定模板
     */
    public static void write(HttpServletResponse response, ExcelUrlModel<?> model) {

        try {
            String fileUrl = model.getFileUrl();
            if (StringUtils.isBlank(fileUrl)) {
                throw new ExcelException("下载失败,下载地址不能为空!");
            }
            URL url = new URL(fileUrl);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();

            ExcelTypeEnum excelType = excelType(fileUrl);
            EasyExcel.write(outputStream(model.getFileName(), Objects.requireNonNull(excelType), response), model.getHeadClass())
                    .excelType(excelType)
                    .needHead(false)
                    .withTemplate(in)
                    .sheet()
                    .doWrite(model.getData());

            in.close();
        } catch (IOException e) {
            log.info("文件下载失败:{}", e.getMessage());
            throw new ExcelException("文件下载失败,fileUrl: {0}", model.getFileUrl());
        }
    }

    /**
     * 批量导出 - 无对象
     */
    public static void write(HttpServletResponse response, ExcelWithoutObjectModel model) {

        try {
            List<String> includeFiledNames = model.getIncludeFiledNames();
            if (CollectionUtils.isNotEmpty(includeFiledNames)) {

                List<List<?>> data = model.getData();
                Iterator<List<String>> iterator = model.getHead().iterator();
                int index = 0;
                while (iterator.hasNext()) {
                    List<String> head = iterator.next();
                    String headName = head.get(head.size() - 1);
                    if (!includeFiledNames.contains(headName)) {

                        iterator.remove();

                        for (List<?> row : data) {
                            row.remove(index);
                        }
                        index--;
                    }
                    index++;
                }
            }

            EasyExcel.write(outputStream(model.getFileName(), response))
                    .registerWriteHandler(HorizontalCellStyleStrategyFactory.exportStyleStrategy())
                    .head(model.getHead())
                    .sheet()
                    .doWrite(model.getData());

        } catch (Exception e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("批量导出失败：{}", cause.getMessage(), cause);
            throw new ExcelException("批量导出失败：{0}", cause.getMessage());
        }

    }

    /* -----------------------------------  下载导入模板  ------------------------------------------------ */

    /**
     * 下载导入模板
     */
    public static void writeTemplate(HttpServletResponse response, Class<?> headClass, String fileName) {

        try {
            EasyExcel.write(outputStream(fileName, response), headClass)
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
     * 下载导入模板 - 支持多sheet
     */
    public static void writeTemplate(HttpServletResponse response, ExcelModel excelModel) {

        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(outputStream(excelModel.getFileName(), response))
                    .registerConverter(new DateConverter())
                    .useDefaultStyle(false)
                    .build();

            List<ExcelModel.Sheet<?>> sheets = excelModel.getSheets();

            for (int i = 0; i < sheets.size(); i++) {

                ExcelModel.Sheet<?> sheet = sheets.get(i);

                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheet.getSheetName())
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .registerWriteHandler(new ImportTempleSheetWriteHandler())
                        .registerWriteHandler(new ImportTempleRowWriteHandler())
                        .registerWriteHandler(new ImportTempleCellWriteHandler())
                        .head(sheet.getHeadClass())
                        .sheetName(sheet.getSheetName())
                        .relativeHeadRowIndex(RELATIVE_HEAD_ROW_INDEX)
                        .includeColumnFiledNames(sheet.getIncludeFiledNames())
                        .build();

                excelWriter.write(sheet.getData(), writeSheet);
            }
        } catch (Exception e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("下载模板失败：{}", cause.getMessage(), cause);
            throw new ExcelException("下载模板失败：{0}", cause.getMessage());
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }

    /* -----------------------------------  批量导入  ------------------------------------------------ */

    /**
     * 批量导入
     */
    public static <E> List<E> read(MultipartFile file, Class<E> clazz) {

        try {
            checkExcelFile(file);

            SimpleAnalysisEventListener<E> listener = SimpleAnalysisEventListener.factory();
            EasyExcel.read(file.getInputStream(), clazz, listener)
                    .headRowNumber(headRowNumber(clazz))
                    .sheet()
                    .doRead();

            List<E> results = listener.getResults();
            if (CollectionUtils.isEmpty(results)) {
                throw new ExcelException("请至少录入一条数据");
            }
            return results;
        } catch (Exception e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("解析异常：{}", cause.getMessage(), cause);
            throw new ExcelException("解析异常：{0}", cause.getMessage());
        }
    }

    /**
     * 批量导入 - 支持多sheet
     */
    @SuppressWarnings("rawtypes")
    public static Map<Class<?>, List<?>> readMultiSheet(MultipartFile file, Class<?>... classes) {


        Map<Class<?>, List<?>> resultMap = Maps.newHashMapWithExpectedSize(classes.length + 1);
        try {
            checkExcelFile(file);

            ExcelReader excelReader = EasyExcel.read(file.getInputStream()).build();
            for (int i = 0; i < classes.length; i++) {

                Class clazz = classes[i];
                SimpleAnalysisEventListener listener = SimpleAnalysisEventListener.factory(true);

                ReadSheet readSheet = EasyExcel.readSheet(i)
                        .head(clazz)
                        .registerReadListener(listener)
                        .headRowNumber(headRowNumber(clazz))
                        .build();

                excelReader.read(readSheet);

                resultMap.put(clazz, listener.getResults());
            }

            if (resultMap.values().stream().allMatch(CollectionUtils::isEmpty)) {
                throw new ExcelException("请至少录入一条数据");
            }
        } catch (Exception e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("解析异常：{}", cause.getMessage(), cause);
            throw new ExcelException("解析异常：{0}", cause.getMessage());
        }
        return resultMap;
    }

    /* -----------------------------------  其他辅助方法  ------------------------------------------------ */

    /**
     * 计算：head 占用行数
     */
    public static Integer headRowNumber(Class<?> clazz) {
        List<ExcelHeadColumn> excelHeadColumns = readHeadColumn(clazz);
        Optional<Integer> maxHeadRowNumber = excelHeadColumns.stream().map(e -> e.getNames().length).max(Integer::compareTo);
        return RELATIVE_HEAD_ROW_INDEX + maxHeadRowNumber.orElse(2);
    }

    /**
     * 提取sheet数据
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> sheet(Map<Class<?>, List<?>> sheetMap, Class<T> calzz) {
        if (MapUtils.isEmpty(sheetMap)) {
            return Lists.newArrayList();
        }
        return (List<T>) sheetMap.getOrDefault(calzz, Lists.newArrayList());
    }

    /**
     * 检查是否是excel文件
     */
    private static void checkExcelFile(MultipartFile file) {
        if (excelType(file.getOriginalFilename()) == null) {
            throw new ExcelException("请选择excel文件");
        }
    }

    /**
     * 获取excel文件类型
     */
    private static ExcelTypeEnum excelType(String originalFilename) {
        String lowerFilename = originalFilename.toLowerCase();
        if (lowerFilename.endsWith(ExcelTypeEnum.XLS.getValue())) {
            return ExcelTypeEnum.XLS;
        }
        if (lowerFilename.endsWith(ExcelTypeEnum.XLSX.getValue())) {
            return ExcelTypeEnum.XLSX;
        }
        return null;
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
                result.setNames(value);
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

    /**
     * 统计行数
     *
     * @param remark 根据换行符判断
     * @return 根据 换行符 和 每行大约字数 统计行数
     */
    public static int countLine(String remark) {
        return countLine(remark, null);
    }

    /**
     * 统计行数
     *
     * @param remark  根据换行符判断
     * @param maxSize 每行大约最大字数
     * @return 根据 换行符 和 每行大约字数 统计行数
     */
    public static int countLine(String remark, Integer maxSize) {
        Matcher strMatcher = Pattern.compile(LINE_BREAK_REGX).matcher(remark);
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

    /**
     * 读取多选项
     */
    public static <T> Set<T> options(String options, Function<String, T> mapper) {
        if (StringUtils.isEmpty(options)) {
            return Sets.newHashSet();
        }
        List<String> list = Arrays.asList(StringUtils.split(options, EasyExcelUtils.MULTI_CHOICE_SEPARATOR));
        return list.stream().map(mapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * 提取字符串中第一个int值
     */
    public static Integer firstInt(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        Matcher matcher = Pattern.compile(NUMBER_REGX).matcher(str);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(0));
        }
        return null;
    }

    /**
     * 重复校验
     */
    public static <T extends ExtraRow, K> void checkRepeat(List<T> list, Function<T, K> keyMapper, String msg) {
        checkRepeat(StringUtils.EMPTY, list, keyMapper, msg);
    }

    /**
     * 重复校验
     */
    public static <T extends ExtraRow, K> void checkRepeat(String sheetName, List<T> list, Function<T, K> keyMapper, String msg) {

        String sheetPreFix = StringUtils.isBlank(sheetName) ? StringUtils.EMPTY : sheetName + "，";
        Map<K, T> map = Maps.newHashMapWithExpectedSize(list.size());
        for (T item : list) {

            T dto = map.get(keyMapper.apply(item));
            if (dto != null) {
                throw new ExcelException("解析异常：{0}第{1}行和第{2}行，{3}", sheetPreFix, dto.getIndex(), item.getIndex(), msg);
            }
            map.put(keyMapper.apply(item), item);
        }
    }

    /**
     * 获取输入流
     */
    private static OutputStream outputStream(String fileName, HttpServletResponse response) {

        return outputStream(fileName, ExcelTypeEnum.XLSX, response);
    }

    /**
     * 获取输入流
     */
    private static OutputStream outputStream(String fileName, ExcelTypeEnum excelType, HttpServletResponse response) {

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + "-" + DateUtils.format(new Date(), DateUtils.DATE_FORMAT_14) + excelType.getValue());
            return response.getOutputStream();
        } catch (IOException e) {

            Throwable cause = Throwables.getRootCause(e);
            log.error("创建文件失败: {}", cause.getMessage(), cause);
            throw new ExcelException("创建文件失败: {0}", cause.getMessage());
        }
    }

}