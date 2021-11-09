package tk.gushizone.rabbitmq;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.rabbitmq.constant.ExchangeConst;
import tk.gushizone.rabbitmq.constant.QueueConst;
import tk.gushizone.rabbitmq.entity.Item;

import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqProducerApplication.class)
public class RabbitmqProducerApplicationTest {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend() throws InterruptedException {

        // String msg = "hello, Spring boot amqp";
        Item msg = new Item(1, "abc");
        amqpTemplate.convertAndSend(ExchangeConst.SPRING_EXCHANGE, "foo.abc", msg);

        // 等待异步监听
        Thread.sleep(1000);
    }

    /**
     * mq 发送
     */
    @Test
    public void testSends() throws InterruptedException {

        // String msg = "hello, Spring boot amqp";
        Item msg = new Item(1, "abc");
        amqpTemplate.convertAndSend(ExchangeConst.SPRING_EXCHANGE, "bar.abc", Lists.newArrayList(msg));

        // 等待异步监听
        Thread.sleep(1000);
    }

    /**
     * 消息不可达测试
     */
    @Test
    public void testErrorSend() throws InterruptedException {

        // String msg = "hello, Spring boot amqp";
        Item msg = new Item(1, "testErrorSend");
        amqpTemplate.convertAndSend(ExchangeConst.SPRING_EXCHANGE, "foobar.abc", Lists.newArrayList(msg));

        // 等待异步监听
        Thread.sleep(1000);
    }

    @Test
    public void testDemoSend() throws InterruptedException {


        Item item = new Item(1, "abc");

        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        // 生产者并不需要关注 queue，只关注 exchange 和 routingKey，routingKey 和 queue 名称相同时也可以传递，走的是默认交换机
        rabbitTemplate.convertAndSend(QueueConst.DEMO_QUEUE, (Object) JSONUtil.toJsonStr(item), cd);

        // 等待异步监听
        Thread.sleep(1000);
    }

    @Test
    public void testTtlSend() throws InterruptedException {


        Item item = new Item(1, "abc");

        // 为消息单独设置TTL
        rabbitTemplate.convertAndSend(QueueConst.TTL_QUEUE, JSONUtil.toJsonStr(item), message -> {
            message.getMessageProperties().setExpiration("10000");

//            消息默认就是持久化
//            message.getMessageProperties().setDeliveryMode(MessageProperties.DEFAULT_DELIVERY_MODE);
            return message;
        });

        // 等待异步监听
        Thread.sleep(1000);
    }

    @Test
    public void testDelaySend() throws InterruptedException {


        Item item = new Item(1, "abc");

        // 为消息单独设置TTL
        rabbitTemplate.convertAndSend(ExchangeConst.DELAY_EXCHANGE, "delay.foo", JSONUtil.toJsonStr(item), message -> {
            message.getMessageProperties().setDelay(10000);
            return message;
        });

        // 等待异步监听
        Thread.sleep(1000);
    }

    @Test
    public void testDlqSend() throws InterruptedException {


        Item item = new Item(1, "abc");

        rabbitTemplate.convertAndSend(QueueConst.SAFE_QUEUE, JSONUtil.toJsonStr(item), message -> {

            // 10s 自动过期，进入死信队列
            message.getMessageProperties().setExpiration("10000");
            return message;
        });

        // 等待异步监听
        Thread.sleep(1000);
    }

}