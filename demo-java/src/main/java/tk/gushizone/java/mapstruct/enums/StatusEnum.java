package tk.gushizone.java.mapstruct.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/8 3:44 下午
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    ABNORMAL(0, "正常"),
    NORMAL(1, "异常"),
    ;

    private Integer value;

    private String name;

    public static StatusEnum valueOf(Integer value) {

        StatusEnum[] values = StatusEnum.values();

        for (StatusEnum statusEnum : values) {
            if (statusEnum.value.equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static StatusEnum nameOf(String name) {

        StatusEnum[] values = StatusEnum.values();

        for (StatusEnum statusEnum : values) {
            if (statusEnum.name.equals(name)) {
                return statusEnum;
            }
        }
        return null;
    }

}
