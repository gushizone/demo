package tk.gushizone.web.validation.controller.dto;

import lombok.Data;
import tk.gushizone.web.validation.groups.DeleteValidateGroup;
import tk.gushizone.web.validation.groups.EditValidateGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-05 22:32
 */
@Data
public class ValidatedParam {

    @NotNull(message = "id为空!", groups = {EditValidateGroup.class, DeleteValidateGroup.class})
    private Integer id;

    @NotEmpty(message = "name不能为空!", groups = EditValidateGroup.class)
    private String name;

}
