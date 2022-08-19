package tk.gushizone.spring.transaction.propagation.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.gushizone.spring.transaction.propagation.dao.ItemMapper;
import tk.gushizone.spring.transaction.propagation.entity.Item;
import tk.gushizone.spring.transaction.propagation.service.ItemService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 22:58
 */
@Slf4j
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


    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
//    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
//    @Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public void sessionA() {
        log.info("{} start...", Thread.currentThread().getName());


        List<Item> list1 = selectAll();
        log.info("{} item size: {}", Thread.currentThread().getName(), list1.size());
        insert(new Item(null, "bar", "bar"));
        log.info("{} insert....", Thread.currentThread().getName());

        Thread.sleep(5000L);


        List<Item> list2 = selectAll();
        log.info("{} item size: {}", Thread.currentThread().getName(), list2.size());

        log.info("{} end.", Thread.currentThread().getName());
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
//    @Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public void sessionB() {
        log.info("{} start...", Thread.currentThread().getName());

        Thread.sleep(2000L);

        insert(new Item(null, "foo", "foo"));
        log.info("{} insert....", Thread.currentThread().getName());


//        Thread.sleep(10_000L);

        log.info("{} end.", Thread.currentThread().getName());
    }

}
