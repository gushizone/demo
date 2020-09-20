package tk.gushizone.java.common.lang;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import tk.gushizone.java.common.entity.Item;

/**
 * @author gushizone@gmail.com
 * @date 2020-07-03 11:44
 */
public class OtherTest {

    @Test
    public void builderUtils(){

        Item item = new Item(18, "张三");

        // Item[id=18,name=张三]
        System.out.println(ToStringBuilder.reflectionToString(item, ToStringStyle.SHORT_PREFIX_STYLE));
    }

    @Test
    public void stringEscapeUtils(){

        //&lt;html&gt;
//        System.out.println(StringEscapeUtils.escapeHtml4("<html>"));
    }

}
