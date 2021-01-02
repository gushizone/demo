package tk.gushizone.excel.easyexcel.utils.write.handler;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 14:47
 */
public class ImportTempleRowWriteHandler extends AbstractRowWriteHandler {

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {

        if (isHead) {
            if (row.getRowNum() == 2) {

                Workbook workbook = writeSheetHolder.getSheet().getWorkbook();

                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 12);
                titleFont.setColor(IndexedColors.WHITE.getIndex());
                titleFont.setFontName("微软雅黑");

                CellStyle titleStyle = workbook.createCellStyle();
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                titleStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());


                titleStyle.setBorderTop(BorderStyle.THIN);
                titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                titleStyle.setBorderRight(BorderStyle.THIN);
                titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                titleStyle.setBorderBottom(BorderStyle.THIN);
                titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                titleStyle.setBorderLeft(BorderStyle.THIN);
                titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    cell.setCellStyle(titleStyle);
                }

            }
        }

    }
}
