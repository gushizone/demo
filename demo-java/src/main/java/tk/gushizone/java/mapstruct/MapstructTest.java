package tk.gushizone.java.mapstruct;

import com.google.common.collect.Lists;
import tk.gushizone.java.mapstruct.dto.ItemDto;
import tk.gushizone.java.mapstruct.mapper.ItemMapper;
import tk.gushizone.java.mapstruct.pojo.Item;

import java.util.Date;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/8 2:52 下午
 */
public class MapstructTest {

    public static void main(String[] args) {

        Item item1 = new Item(1L, "foo", 0, new Date());
        Item item2 = new Item(2L, "bar", 1, new Date());

        ItemDto dto = ItemMapper.INSTANCE.toDto(item1);
        System.out.println(dto);

        List<ItemDto> dtoList = ItemMapper.INSTANCE.toDto(Lists.newArrayList(item1, item2));
        System.out.println(dtoList);

        Item item = ItemMapper.INSTANCE.toPojo(dto);
        System.out.println(item);

        List<Item> items = ItemMapper.INSTANCE.toPojo(dtoList);
        System.out.println(items);


    }

}
