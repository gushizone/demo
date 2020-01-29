package tk.gushizone.rabbitmq.test.spring;

import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.rabbitmq.constant.ExchangeConst;
import tk.gushizone.rabbitmq.spring.RabbitMqApplication;
import tk.gushizone.rabbitmq.util.ConnectionUtil;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-29 21:59
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqApplication.class)
public class RabbitMqApplicationTest {

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Test
    public void testConnect() throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) {
            log.warn("rabbitmq 连接成功!");
        }
    }

    /**
     * mq 发送
     */
    @Test
    public void testSend() throws InterruptedException {

        String msg = "hello, Spring boot amqp";
        amqpTemplate.convertAndSend(ExchangeConst.SPRING_EXCHANGE, "a.b", msg);
        // 等待10秒后再结束
        Thread.sleep(10000);
    }
}
