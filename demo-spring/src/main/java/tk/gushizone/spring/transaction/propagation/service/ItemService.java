package tk.gushizone.spring.transaction.propagation.service;


import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import tk.gushizone.spring.transaction.propagation.entity.Item;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 21:42
 */
public interface ItemService {

    void deleteAll();

    List<Item> selectAll();

    void insert(Item record);

    void sessionA();

    void sessionB();
}