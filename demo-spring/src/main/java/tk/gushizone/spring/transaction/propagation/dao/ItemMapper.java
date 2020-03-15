package tk.gushizone.spring.transaction.propagation.dao;


import tk.gushizone.spring.transaction.propagation.entity.Item;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 21:42
 */
public interface ItemMapper {

    int insert(Item record);

    int deleteAll();

    List<Item> selectAll();
}