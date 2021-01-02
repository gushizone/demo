package tk.gushizone.excel.easyexcel.utils.write.converter;


import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;
import tk.gushizone.excel.easyexcel.utils.EasyExcelUtils;

import java.util.Date;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:05
 */
public class DateConverter extends DateStringConverter {

    @Override
    @SuppressWarnings("rawtypes")
    public CellData convertToExcelData(Date value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new CellData(DateUtils.format(value, EasyExcelUtils.DATE_FORMAT));
        } else {
            return new CellData(DateUtils.format(value, contentProperty.getDateTimeFormatProperty().getFormat()));
        }
    }
}
