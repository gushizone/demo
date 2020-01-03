package tk.gushizone.json.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tk.gushizone.json.jackson.entity.Item;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * jackson 提供了三种不同的方法来处理JSON： TODO 三种方式并不是相互独立，可以配合使用
 * 流式API - 读取并将JSON内容写入作为离散事件。
 *      JsonParser读取数据，而JsonGenerator写入数据。
 *      它是三者中最有效的方法，是最低的开销和最快的读/写操作。它类似于Stax解析器XML。
 * 树模型 - 准备JSON文件在内存里以树形式表示。
 *      ObjectMapper构建JsonNode节点树。
 *      这是最灵活的方法。它类似于XML的DOM解析器。
 * 数据绑定 - 转换JSON并从POJO（普通Java对象）使用属性访问或使用注释。
 *      它有两个类型。 简单的数据绑定 - 转换JSON和Java Maps, Lists, Strings, Numbers, Booleans 和null 对象。
 *      全部数据绑定 - 转换为JSON从任何JAVA类型。
 *
 * @author gushizone@gmail.com
 * @date 2020-01-02 17:48
 */
@Slf4j
public class JacksonTest {


    /**
     * ObjectMapper: java 和 json 的相互转换。
     */
    @Test
    public void testJava2json() {
        String userJson1 = "{\"id\":1,\"name\":\"Foo\",\"price\":123.3210,\"createTime\":693244800000}";

        // 第1步：创建ObjectMapper对象。
        ObjectMapper mapper = new ObjectMapper();
        // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        SimpleModule module = new SimpleModule();
        // 序列化成时间戳
        // module.addSerializer(Date.class, new DateSerializer(true, null));
        // 自定义序列化格式
        module.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat("yyyy-MM-dd HH:ss")));
        mapper.registerModule(module);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        try {
            // 第2步：反序列化JSON到对象。
            Item item = mapper.readValue(userJson1, Item.class);
            log.info(item.toString());

            // 输出格式化：添加缩进
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            // 第3步：序列化对象到JSON。
            userJson1 = mapper.writeValueAsString(item);

            log.info(userJson1);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 方式一、数据（类型）绑定
     * 优势：操作简单
     * 劣势：读需要知道具体数据类型，若没对应属性，数据就会丢失
     * 应用场景：适合已约定类型的接口应用，数据绑定
     */
    @Test
    public void testDataBinding() throws ParseException {

        Item item = Item.builder()
                .id(1)
                .name("Foo")
                .price(new BigDecimal("123.3210"))
                .createTime(new SimpleDateFormat("yyyy-MM-dd").parse("1991-12-21"))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // object to json
            String strJson = objectMapper.writeValueAsString(item);
            log.info(strJson);
            // {"id":1,"name":"Foo","price":123.3210,"createTime":693244800000}

            // json to object
            Item obj = objectMapper.readValue(strJson, Item.class);
            log.info(obj.toString());
            // Item(id=1, name=Foo, price=123.3210, createTime=Sat Dec 21 00:00:00 CST 1991)

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式二、树模型
     * 优势：操作灵活
     * 劣势：写需要对树节点逐个操作，且节点类型固定；读或需要知道具体数据类型(缺失属性会报错)，或需要对树节点逐个操作
     * 应用场景：json 属性操作，遍历等
     * <p>
     * TODO 工具类将 jsonNode 对象转变为 jdk 或 自定义 对象。
     */
    @Test
    public void testTreeModel() throws ParseException {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // object to json
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("id", 1);
            rootNode.put("name", "Foo");
            rootNode.put("price", new BigDecimal("123.3210"));
            rootNode.put("createTime", new SimpleDateFormat("yyyy-MM-dd").parse("1991-12-21").getTime());
            String strJson = objectMapper.writeValueAsString(rootNode);
            log.info(strJson);
            // {"id":1,"name":"Foo","price":123.321,"createTime":693244800000}

            // 方式一 json to object: jsonNode
            JsonNode rootNode2 = objectMapper.readTree(strJson);
            log.info(rootNode2.toString());
            // {"id":1,"name":"Foo","price":123.321,"createTime":693244800000}
            Iterator<String> fields = rootNode2.fieldNames();
            while (fields.hasNext()) {
                String field = fields.next();
                log.info(field + ":" + rootNode2.get(field));
                // id:1
                // name:"Foo"
                // price:123.321
                // createTime:693244800000
            }

            // 方式二 json to object: javabean
            Item obj = objectMapper.treeToValue(rootNode, Item.class);
            log.info(obj.toString());
            // Item(id=1, name=Foo, price=123.321, createTime=Sat Dec 21 00:00:00 CST 1991)

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 流式API
     * 方式一、节点操作
     */
    @Test
    public void testSteam1() throws ParseException {

        // object to json, 方式一
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGen = null;
        StringWriter sw = new StringWriter();

        try {
            // TODO StringWriter ： 内置StringBuffer对象
            jsonGen = jsonFactory.createGenerator(sw);
            jsonGen.writeStartObject();
            jsonGen.writeNumberField("id", 1);
            jsonGen.writeStringField("name", "Foo");
            jsonGen.writeNumberField("price", new BigDecimal("123.3210"));
            jsonGen.writeNumberField("createTime", new SimpleDateFormat("yyyy-MM-dd").parse("1991-12-21").getTime());
            jsonGen.writeEndObject();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jsonGen != null) {
                try {
                    jsonGen.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // JsonGenerator close 后才会写入
        log.info(sw.toString());
        // {"id":1,"name":"Foo","price":123.3210,"createTime":693244800000}


        // json to object
        try {
            JsonParser jsonParser = jsonFactory.createParser(sw.toString());
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldname = jsonParser.getCurrentName();
                if ("name".equals(fieldname)) {
                    jsonParser.nextToken();
                    log.info(jsonParser.getText());
                    // Foo
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 流式API
     * 方式二、对象绑定
     */
    @Test
    public void testSteam2() throws ParseException {
        Item item = Item.builder()
                .id(1)
                .name("Foo")
                .price(new BigDecimal("123.3210"))
                .createTime(new SimpleDateFormat("yyyy-MM-dd").parse("1991-12-21"))
                .build();

        // object to json
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGen = null;
        StringWriter sw = new StringWriter();

        try {
            // TODO StringWriter ： 内置StringBuffer对象
            jsonGen = jsonFactory.createGenerator(sw);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(jsonGen, item);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jsonGen != null) {
                try {
                    jsonGen.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // JsonGenerator close 后才会写入
        log.info(sw.toString());
        // {"id":1,"name":"Foo","price":123.3210,"createTime":693244800000}


        JsonParser jsonParser = null;
        // json to object
        try {
            jsonParser = jsonFactory.createParser(sw.toString());

            ObjectMapper objectMapper = new ObjectMapper();
            Item obj = objectMapper.readValue(sw.toString(), Item.class);
            log.info(obj.toString());
            // Item(id=1, name=Foo, price=123.3210, createTime=Sat Dec 21 00:00:00 CST 1991)

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jsonParser != null) {
                try {
                    jsonParser.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * JSON 转 XML
     */
    @Test
    public void testJson2xml() {
        String userJson1 = "{\"id\":1,\"name\":\"Foo\",\"price\":123.3210}";

        ObjectMapper objectMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();
        StringWriter sw = new StringWriter();
        try {
            // json to xml
            JsonNode root = objectMapper.readTree(userJson1);
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String xml = xmlMapper.writeValueAsString(root);
            log.info(xml);
            //  <ObjectNode>
            //    <id>1</id>
            //    <name>Foo</name>
            //    <price>123.321</price>
            //  </ObjectNode>


            // xml to json
            JsonParser jsonParser = xmlMapper.getFactory().createParser(xml);
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(sw);
            while (jsonParser.nextToken() != null) {
                jsonGenerator.copyCurrentEvent(jsonParser);
            }
            jsonParser.close();
            jsonGenerator.close();

            log.info(sw.toString());
            // {"id":"1","name":"Foo","price":"123.321"}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
