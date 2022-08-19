package tk.gushizone.spring.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.spring.SpringDemoApplication;
import tk.gushizone.spring.transaction.propagation.service.ItemService;
import tk.gushizone.spring.transaction.propagation.service.ItemStockService;
import tk.gushizone.spring.transaction.propagation.service.StockService;

import javax.annotation.Resource;

/**
 * spring事务的传播测试
 *
 * @author gushizone@gmail.com
 * @date 2020-01-30 18:16
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDemoApplication.class)
public class PropagationTest {

    @Resource
    private ItemService itemService;
    @Resource
    private StockService stockService;
    @Resource
    private ItemStockService itemStockService;


    @Before
    public void reset() {
        log.warn("======清空数据======");
        itemStockService.deleteAll();
        log.warn("======准备操作======");
    }

    @After
    public void log() {
        log.warn("======操作结束, 展示结果======");
        log.warn("items : {}", itemService.selectAll());
        log.warn("stocks : {}", stockService.selectAll());
    }

    @Test
    public void testIsolation() throws InterruptedException {
        itemStockService.testIsolation();


        Thread.sleep(20_000L);
    }

    @Test
    public void test() {
        itemStockService.insert();
    }

    /**
     * 因为事务基于动态代理回滚
     * 而 this 调用的是实例方法,不是代理方法
     */
    @Test
    public void testThis() {
        itemStockService.testThis();
    }

}
