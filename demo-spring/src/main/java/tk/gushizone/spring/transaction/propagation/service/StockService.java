package tk.gushizone.spring.transaction.propagation.service;


import tk.gushizone.spring.transaction.propagation.entity.Stock;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 21:42
 */
public interface StockService {

    void deleteAll();

    List<Stock> selectAll();

    void insert(Stock record);

}