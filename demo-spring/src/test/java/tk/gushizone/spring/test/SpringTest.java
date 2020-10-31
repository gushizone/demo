package tk.gushizone.spring.test;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import tk.gushizone.spring.transaction.propagation.entity.Item;

/**
 * @author gushizone@gmail.com
 * @date 2020-10-14 16:55
 */
public class SpringTest {

    @Test
    public void test () {

        Item item1 = new Item(12423535L, "abc", "r");
        Item item2 = new Item();

        BeanUtils.copyProperties(item1, item2);


    }


}
