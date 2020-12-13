//
package tk.gushizone.web.json.interceptor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class JsonSerializerInterceptor extends JsonSerializer<Object> {

    private static final ZoneOffset CTT;

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        try {
            if (object instanceof Long) {
                jsonGenerator.writeString(object.toString());
            } else if (object instanceof LocalDateTime) {
                jsonGenerator.writeNumber(((LocalDateTime) object).toInstant(CTT).toEpochMilli());
            } else if (object instanceof LocalDate) {
                jsonGenerator.writeNumber(((LocalDate) object).atStartOfDay().toInstant(CTT).toEpochMilli());
            } else if (object instanceof BigDecimal) {
                jsonGenerator.writeNumber(((BigDecimal) object).setScale(4, RoundingMode.HALF_UP));
            } else {
                jsonGenerator.writeObject(object);
            }

        } catch (Exception var8) {
            throw new RuntimeException("序列化失败", var8);
        }
    }

    static {
        CTT = OffsetDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).getOffset();
    }
}
