package tk.gushizone.java.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/8 2:45 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityDto {

    private Long id;

    private String name;

    private Integer status;

    private Date createTime;

}
