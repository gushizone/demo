package tk.gushizone.excel.easyexcel.utils.write.handler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import tk.gushizone.excel.easyexcel.utils.EasyExcelUtils;
import tk.gushizone.excel.easyexcel.utils.common.ExcelPropertyX;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 14:48
 */
public class ImportTempleCellWriteHandler extends AbstractCellWriteHandler {

    @SneakyThrows
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        if (isHead) {
            if (NumberUtils.INTEGER_ONE.equals(relativeRowIndex)) {

                Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
                Sheet sheet = writeSheetHolder.getSheet();

                Font font = workbook.createFont();
                font.setFontHeightInPoints((short) 11);
                font.setFontName("微软雅黑");

                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);

                Class<?> clazz = writeSheetHolder.getClazz();
                Field field = clazz.getDeclaredField(head.getFieldName());

                if (field.isAnnotationPresent(ExcelPropertyX.class)) {
                    ExcelPropertyX excelPropertyX = field.getAnnotation(ExcelPropertyX.class);

                    if (excelPropertyX.required()) {
                        font.setColor(IndexedColors.RED.getIndex());
                    }

                    String note = StringUtils.EMPTY;
                    if (StringUtils.isNotEmpty(excelPropertyX.note())) {
                        note = excelPropertyX.note();
                    } else if (ArrayUtils.isNotEmpty(excelPropertyX.options())) {
                        note = StringUtils.join(excelPropertyX.options(), "\n");
                    }
                    // todo 暂时解决office批注无法自适应大小
                    int lineCount = EasyExcelUtils.countLine(note, 6);
                    if (StringUtils.isNotEmpty(note)) {
                        Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
                        XSSFClientAnchor xssfClientAnchor = new XSSFClientAnchor(0, 0, 0, 0,
                                cell.getColumnIndex(), cell.getRowIndex(), (short) cell.getColumnIndex() + 1, cell.getRowIndex() + lineCount + 1);
                        Comment comment = drawingPatriarch.createCellComment(xssfClientAnchor);
                        comment.setString(new XSSFRichTextString("说明：\n" + note));
                        cell.setCellComment(comment);
                    }
                }
            }
        }
    }
}
