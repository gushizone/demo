package tk.gushizone.excel.easyexcel.utils.write.handler;

import com.alibaba.excel.write.handler.AbstractSheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import tk.gushizone.excel.easyexcel.utils.EasyExcelUtils;
import tk.gushizone.excel.easyexcel.utils.common.ExcelHeader;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 14:47
 */
public class ImportTempleSheetWriteHandler extends AbstractSheetWriteHandler {

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        if (writeSheetHolder.getSheetNo() != 0) {
            return;
        }

        Class<?> headClass = writeSheetHolder.getClazz();

        ExcelHeader excelHeader = headClass.getAnnotation(ExcelHeader.class);

        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet sheet = writeSheetHolder.getSheet();

        int maxIndex = writeSheetHolder.getExcelWriteHeadProperty().getHeadMap()
                .keySet().stream().max(Integer::compareTo).orElse(10);

        Row row1 = sheet.createRow(0);
        Cell titleCell = row1.createCell(0);
        titleCell.setCellValue(excelHeader.value());

        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.WHITE.getIndex());
        titleFont.setFontName("微软雅黑");

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, maxIndex));

        Row row2 = sheet.createRow(1);

        int lineCount = EasyExcelUtils.countLine(excelHeader.remark());
        row2.setHeight((short) (lineCount * 400));

        Cell remarkCell = row2.createCell(0);
        remarkCell.setCellValue(excelHeader.remark());

        Font remarkFont = workbook.createFont();
        remarkFont.setFontHeightInPoints((short) 11);
        remarkFont.setColor(IndexedColors.GREY_80_PERCENT.getIndex());
        remarkFont.setFontName("微软雅黑");

        CellStyle remarkStyle = workbook.createCellStyle();
        remarkStyle.setFont(remarkFont);
        remarkStyle.setVerticalAlignment(VerticalAlignment.TOP);
        remarkStyle.setWrapText(true);
        remarkCell.setCellStyle(remarkStyle);
        sheet.addMergedRegionUnsafe(new CellRangeAddress(1, 1, 0, maxIndex));
    }


}
