package tk.gushizone.excel.easyexcel.utils.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelHeader {

    String value();

    String remark() default "备注（请勿删除已有文字内容，直接添加数据后上传）：\n" +
            "1、红色字段为必填信息；";
}
