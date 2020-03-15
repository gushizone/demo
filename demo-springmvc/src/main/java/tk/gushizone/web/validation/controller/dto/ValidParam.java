package tk.gushizone.web.validation.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-05 22:32
 */
@Data
public class ValidParam {

    @NotNull(message = "id不能为空")
    private Integer id;

    private String name;
}
