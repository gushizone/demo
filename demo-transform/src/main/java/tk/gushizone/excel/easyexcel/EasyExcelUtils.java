package tk.gushizone.excel.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-11-08 20:01
 */
public class EasyExcelUtils {


    public static void write(HttpServletResponse response, ExcelModel excelModel) {

//        File tmpFile = FileUtils.createTmpFile(UUID.randomUUID().toString() + ExcelTypeEnum.XLSX.getValue());

        ExcelWriter excelWriter = EasyExcel.write(getOutputStream(excelModel.getFileName(), response))
                .useDefaultStyle(false)
                .build();

        List<ExcelModel.Sheet<?>> sheets = excelModel.getSheets();

        for (int i = 0; i < sheets.size(); i++) {

            ExcelModel.Sheet<?> sheet = sheets.get(i);

            WriteSheet writeSheet = EasyExcel.writerSheet(i, sheet.getSheetName())
                    .head(sheet.getHeadClass())
//                    .registerWriteHandler(new CustomerCommonStrategy(sheet.getHeadClass()))
                    .build();

            excelWriter.write(sheet.getData(), writeSheet);
        }

        excelWriter.finish();
    }


    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {


        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // swagger，postman 等依然存在中文乱码，chrome 正常
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("创建文件失败！");
        }
    }

}
