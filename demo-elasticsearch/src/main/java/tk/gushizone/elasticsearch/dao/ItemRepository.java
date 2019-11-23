package tk.gushizone.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import tk.gushizone.elasticsearch.pojo.Item;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2019-09-14 14:58
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    List<Item> findByTitle(String title);

}
