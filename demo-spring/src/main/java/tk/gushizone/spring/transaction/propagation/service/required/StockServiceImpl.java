package tk.gushizone.spring.transaction.propagation.service.required;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.gushizone.spring.transaction.propagation.dao.StockMapper;
import tk.gushizone.spring.transaction.propagation.entity.Stock;
import tk.gushizone.spring.transaction.propagation.service.StockService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 22:58
 */
@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockMapper stockMapper;

    @Override
    public void deleteAll() {
        stockMapper.deleteAll();
    }

    @Override
    public List<Stock> selectAll() {
        return stockMapper.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
//    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void insert(Stock record) {
//        int i = 1 / 0;
        stockMapper.insert(record);
        int j = 1 / 0;
    }

}
