package tk.gushizone.json.jackson.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tk.gushizone.json.jackson.utils.serializer.BigDecimalSerializer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * json 工具类
 *
 * @author gushizone@gmail.com
 * @date 2020-01-03 20:53
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 支持单引号（兼容 js ）
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        // 支持字段名不带引号（兼容 js ）
        OBJECT_MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);

        // 接收 "" 等同 null
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        // 遇到未知属性时不抛出异常
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 时间类型正常输出（默认Date , Calendar会输出为数字，这里禁用）
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // null 属性不进行序列化
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 自定义 序列化格式 与 反序列化格式
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


        SimpleModule module = new SimpleModule();
        // 序列化成时间戳
        // module.addSerializer(Date.class, new DateSerializer(true, null));
        // 自定义序列化格式
        module.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat("yyyy-MM-dd")));
        module.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        OBJECT_MAPPER.registerModule(module);
    }

    /**
     * 对象 转 json字符串 （适用集合）
     */
    public static <T> String objToJson(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * json字符串 转 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonStr) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) jsonStr : OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * json字符串 转 list集合
     */
    public static <E> List<E> jsonToList(String jsonStr, Class<E> elementClass) {
        if (StringUtils.isBlank(jsonStr) || elementClass == null) {
            return Lists.newArrayList();
        }
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, elementClass);
        try {
            return OBJECT_MAPPER.readValue(jsonStr, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error", e);
            return Lists.newArrayList();
        }
    }
}