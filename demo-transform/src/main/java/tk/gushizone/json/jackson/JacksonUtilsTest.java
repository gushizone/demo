package tk.gushizone.json.jackson;

import org.junit.Test;
import tk.gushizone.json.jackson.entity.GoodItem;
import tk.gushizone.json.jackson.entity.Item;
import tk.gushizone.json.jackson.utils.JsonUtils;

/**
 * jackson 反序列化需要无参构造
 *
 * @author gushizone@gmail.com
 * @date 2020-03-19 18:36
 */
public class JacksonUtilsTest {

    @Test
    public void test() {
        Item item = GoodItem.builder()
                .good("aaa")
                .clazz(String.class)
                // .function(i -> i.getClass() + String.valueOf(i.hashCode()))
                .build();

        String s = JsonUtils.objToJson(item);
        System.out.println(s);

        GoodItem goodItem = JsonUtils.jsonToObj(s, GoodItem.class);
        System.out.println(goodItem);
    }
}
