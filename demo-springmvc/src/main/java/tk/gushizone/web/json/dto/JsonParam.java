package tk.gushizone.web.json.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.gushizone.web.json.annotaion.JsonSerializeX;

import java.math.BigDecimal;

/**
 * @author gushizone@gmail.com
 * @date 2020-12-13 22:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonParam {

    @JsonSerializeX
    private BigDecimal bigDecimal;


}
