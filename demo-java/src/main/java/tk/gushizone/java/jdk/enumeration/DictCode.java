package tk.gushizone.java.jdk.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gushizone@gmail.com
 * @date 2019-04-08 16:00
 */
@Getter
@AllArgsConstructor
public enum DictCode {

    SUCCESS(0, "成功"),

    WARNING(1, "警告"),

    ERROR(-1, "错误");


    private int code;

    private String codeName;
}
