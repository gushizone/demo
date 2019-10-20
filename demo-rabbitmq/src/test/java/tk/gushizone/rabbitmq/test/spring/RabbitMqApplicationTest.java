package tk.gushizone.rabbitmq.test.spring;

import com.rabbitmq.client.Connection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.rabbitmq.spring.RabbitMqApplication;
import tk.gushizone.rabbitmq.util.ConnectionUtil;


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
            System.out.println("rabbitmq 连接成功!");
        }
    }

    /**
     * mq 发送
     * @throws InterruptedException
     */
    @Test
    public void testSend() throws InterruptedException {
        String msg = "hello, Spring boot amqp";
        amqpTemplate.convertAndSend("spring.test.exchange","a.b", msg);
        // 等待10秒后再结束
        Thread.sleep(10000);
    }
}
