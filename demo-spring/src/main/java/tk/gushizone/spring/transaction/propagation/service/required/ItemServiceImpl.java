package tk.gushizone.spring.transaction.propagation.service.required;

import org.springframework.stereotype.Service;
import tk.gushizone.spring.transaction.propagation.dao.ItemMapper;
import tk.gushizone.spring.transaction.propagation.entity.Item;
import tk.gushizone.spring.transaction.propagation.service.ItemService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 22:58
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemMapper itemMapper;

    @Override
    public void deleteAll() {
        itemMapper.deleteAll();
    }

    @Override
    public List<Item> selectAll() {
        return itemMapper.selectAll();
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void insert(Item record) {
//        int i = 1 / 0;
        itemMapper.insert(record);
//        int j = 1 / 0;
    }

}
