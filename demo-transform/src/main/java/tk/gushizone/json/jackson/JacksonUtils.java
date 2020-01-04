package tk.gushizone.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 基于 jackson 的 json, xml 工具类
 *
 * @author gushizone@gmail.com
 * @date 2020-01-03 20:53
 */
public class JacksonUtils {

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
     * Object 转 json
     */
    public static String object2Json(Object obj) {
        StringWriter sw = new StringWriter();
        JsonGenerator jsonGen = null;

        try {
            jsonGen = new JsonFactory().createGenerator(sw);
            OBJECT_MAPPER.writeValue(jsonGen, obj);
        } catch (IOException e) {
            throw new RuntimeException("序列化失败！", e);
        } finally {
            if (jsonGen != null) {
                try {
                    jsonGen.close();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败！", e);
                }
            }
        }

        return sw.toString();
    }

    /**
     * json字符串 转 Object
     */
    public static Object json2Object(String jsonStr) {
        try {
            return json2Object(OBJECT_MAPPER.readTree(jsonStr));
        } catch (IOException e) {
            throw new RuntimeException("序列化失败: " + jsonStr, e);
        }
    }

    /**
     * json字符串 转 Object
     */
    public static <T> T json2Object(String jsonStr, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new RuntimeException("序列化失败: " + jsonStr, e);
        }
    }

    /**
     * jsonNode 转 Object
     * JsonNodeType: https://tools.ietf.org/html/rfc4627
     */
    public static Object json2Object(JsonNode jsonNode) {
        if (JsonNodeType.OBJECT.equals(jsonNode.getNodeType())) {
            Map<String, Object> map = new HashMap<>();
            Iterator<String> fieldNames = jsonNode.fieldNames();
            while (fieldNames.hasNext()) { // TODO
                String fieldName = fieldNames.next();
                map.put(fieldName, json2Object(jsonNode.path(fieldName)));
            }
            return map;
        }
        if (JsonNodeType.ARRAY.equals(jsonNode.getNodeType())) {
            List<Object> list = new ArrayList<>();
            // TODO 测试一下各种类型，特别是该类型 : ArrayNode 意义？
            for (JsonNode node : jsonNode) {
                list.add(node);
            }
            return list;
        }
        if (JsonNodeType.STRING.equals(jsonNode.getNodeType())) {
            return jsonNode.asText(StringUtils.EMPTY);
        }
        if (jsonNode.isBoolean()) {
            return jsonNode.booleanValue();
        }
        if (jsonNode.isFloat()) {
            return jsonNode.floatValue();
        }
        if (jsonNode.isDouble()) {
            return jsonNode.doubleValue();
        }
        if (jsonNode.isLong()) {
            return jsonNode.longValue();
        }
        if (jsonNode.isInt()) {
            return jsonNode.intValue();
        }
        if (jsonNode.isShort()) {
            return jsonNode.shortValue();
        }
        if (jsonNode.isNumber()) {
            return jsonNode.numberValue();
        }
        return jsonNode.toString();
    }
}
