package tk.gushizone.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author gushizone
 * @date 2022/10/17 14:52
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Override
    public void save(Product product) {

        Product product1 = Product.builder()
                .id(1L)
                .name("foo")
                .price(new BigDecimal("100"))
                .build();

        // 入库...

        // todo 默认是同步阻塞的, 需要封装, 确保事务提交成功后发布事件.
        // 同步 ES
        // 事件发布
//        eventPublisher.publishEvent(new ProductEvent(product1));
        eventPublisher.publishEvent(new GenericEvent<>(product1));

    }
}
