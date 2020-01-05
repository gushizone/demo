package tk.gushizone.web.validation.controller.dto;

import lombok.Data;
import tk.gushizone.web.validation.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-05 22:32
 */
@Data
public class ValidatorParam {

    private Integer id;

    @Length(min = 6)
    private String name;
}
