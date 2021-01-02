package tk.gushizone.excel.easyexcel.utils.exception;


import lombok.Builder;

import java.text.MessageFormat;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:06
 */
@Builder
public class ExcelException extends RuntimeException {

    private String message;

    public ExcelException(String message, Object... params) {
        this.message = MessageFormat.format(message, params);
    }
}
