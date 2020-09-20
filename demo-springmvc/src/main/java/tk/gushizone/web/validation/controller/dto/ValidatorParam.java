package tk.gushizone.web.validation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tk.gushizone.web.validation.constraints.Length;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-05 22:32
 */
@Data
@AllArgsConstructor
public class ValidatorParam {

    private Integer id;

    @Length(min = 6)
    private String name;
}
