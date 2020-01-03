package tk.gushizone.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 自定序列化器 TODO
 *
 * @author gushizone@gmail.com
 * @date 2020-01-03 20:52
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null){
            gen.writeString(StringUtils.EMPTY);
        } else {
            // 去尾0，不使用科学计数法
            gen.writeString(value.stripTrailingZeros().toPlainString());
        }
    }
}
