package tk.gushizone.web.json.interceptor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.apache.commons.lang3.StringUtils;
import tk.gushizone.web.json.annotaion.JsonDeserializeX;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

public class JsonDeserializerInterceptor extends JsonDeserializer<Object> implements ContextualDeserializer {

    private static final ZoneOffset CTT;
    private static final String SEPARATE = ",";
    private Class<?> clazz;
    private Class<?> rawClass;

    public JsonDeserializerInterceptor() {
    }

    public JsonDeserializerInterceptor(Class<?> clazz, Class<?> rawClass) {
        this.clazz = clazz;
        this.rawClass = rawClass;
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            if (jsonParser != null && StringUtils.isNotEmpty(jsonParser.getText())) {
                if (this.clazz == LocalDateTime.class) {
                    return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getLongValue()), CTT);
                } else if (this.clazz == LocalDate.class) {
                    return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getLongValue()), CTT).toLocalDate();
                } else if (!List.class.isAssignableFrom(this.clazz)) {
                    return jsonParser.getText();
                } else {
                    String str = jsonParser.getText();
                    if (StringUtils.isEmpty(str)) {
                        return null;
                    }
                }
            }
            return null;
        } catch (Exception var15) {
            throw new RuntimeException("反序列化失败", var15);
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            JsonDeserializeX deserializerAnnotation = beanProperty.getAnnotation(JsonDeserializeX.class);
            if (deserializerAnnotation == null) {
                deserializerAnnotation = beanProperty.getContextAnnotation(JsonDeserializeX.class);
            }

            return  (deserializerAnnotation != null ? new JsonDeserializerInterceptor(beanProperty.getType().getRawClass(), deserializerAnnotation.rawClass()) : deserializationContext.findNonContextualValueDeserializer(beanProperty.getType()));
        } else {
            return this;
        }
    }

    static {
        CTT = OffsetDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).getOffset();
    }
}
