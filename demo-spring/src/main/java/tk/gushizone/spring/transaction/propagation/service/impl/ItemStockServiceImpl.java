package tk.gushizone.spring.transaction.propagation.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.gushizone.spring.transaction.propagation.entity.Item;
import tk.gushizone.spring.transaction.propagation.entity.Stock;
import tk.gushizone.spring.transaction.propagation.service.ItemService;
import tk.gushizone.spring.transaction.propagation.service.ItemStockService;
import tk.gushizone.spring.transaction.propagation.service.StockService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 22:58
 */
@Slf4j
@Service
public class ItemStockServiceImpl implements ItemStockService {

    @Resource
    private ItemService itemService;
    @Resource
    private StockService stockService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        itemService.deleteAll();
        stockService.deleteAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void insert() {
//        int i = 1 / 0;
        itemService.insert(Item.SAMPLE);
//        int j = 1 / 0;
        stockService.insert(Stock.SAMPLE);
//        int k = 1 / 0;
    }

    @Override
    public void testThis() {

        this.insert();

    }

    /**
     * 测试隔离级别
     */
    @Override
    public void testIsolation() {

        new Thread(() -> itemService.sessionA()).start();
        new Thread(() -> itemService.sessionB()).start();

    }

}
