package tk.gushizone.mybatis.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:37
 */
@Getter
@AllArgsConstructor
public enum SortTypeEnum {


    ASC(1, "asc"),

    DESC(2, "desc");


    private Integer codeValue;

    private String codeText;

    public static SortTypeEnum getInstance(Integer codeValue) {
        SortTypeEnum[] values = SortTypeEnum.values();
        for (SortTypeEnum value : values) {
            if (codeValue.intValue() == value.getCodeValue().intValue()) {
                return value;
            }
        }
        return null;
    }
}
