package tk.gushizone.spring.transaction.propagation.dao;


import tk.gushizone.spring.transaction.propagation.entity.Stock;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 21:42
 */
public interface StockMapper {

    int insert(Stock record);

    int deleteAll();

    List<Stock> selectAll();
}