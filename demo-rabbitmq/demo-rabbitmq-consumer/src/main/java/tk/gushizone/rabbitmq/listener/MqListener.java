package tk.gushizone.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import tk.gushizone.rabbitmq.constant.ExchangeConst;
import tk.gushizone.rabbitmq.constant.QueueConst;
import tk.gushizone.rabbitmq.entity.Item;

import java.io.IOException;
import java.util.List;

/**
 * rabbitmq 默认使用 java 的序列化机制，
 * 故java的信息可以被正常传递，如枚举类型等
 *
 * @author gushizone@gmail.com
 * @date 2020-01-29 21:58
 */
@Slf4j
@Component
public class MqListener {

    /**
     * 接收单个对象 - 必须类型相同
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueConst.SPRING_QUEUE_1, durable = "true"),
            exchange = @Exchange(value = ExchangeConst.SPRING_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"foo.*"}))
    @RabbitHandler
    public void rec(Item msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {

        log.warn("接收到消息：{}", msg);
        channel.basicAck(tag, false);
    }

    /**
     * 接收集合 （因为泛型擦除，只在类型转换时才会报错）
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueConst.SPRING_QUEUE_2, durable = "true"),
            exchange = @Exchange(value = ExchangeConst.SPRING_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"bar.#"}))
    @RabbitHandler
    public void listRec(List<String> list, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {

        log.warn("接收到消息：{}", list);
        channel.basicAck(tag, false);
    }

    /**
     * 基础示例
     */
    @RabbitListener(queues = QueueConst.DEMO_QUEUE)
    public void demoRec(Channel channel, Message message) throws IOException {
        try {
            // 消费限流，最大 1
            channel.basicQos(0, 1, false);

            // 需要特殊配置，建议封装统一消息实体进行传递
            log.warn("接收消息 CorrelationId: {}", message.getMessageProperties().getCorrelationId());

            String body = new String(message.getBody());

            // 模拟异常
            int i = 1 / 0;

            log.warn("消费成功：[{}]", body);

            // 手动 ack: 确认收到消息，false只确认当前一个消息，true确认获得的所有消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {

            log.error("消费失败：[{}]", message, e);

            // 当消息回滚到消息队列时，这条消息不会回到队列尾部，而是仍是在队列头部，
            // 这时消费者会又接收到这条消息，如果想消息进入队尾，须确认消息后再次发送消息。
//            channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
//                    message.getMessageProperties().getReceivedRoutingKey(),
//                    MessageProperties.PERSISTENT_TEXT_PLAIN,
//                    message.getBody());

            // 是否非首次投递
//            if (message.getMessageProperties().getRedelivered()) {
//
//                // 拒绝消息，不再重新入队，若有死信队列则进入
//                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//            } else {
//
//                // 首次投递消费失败，重新入队
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//            }

            // 确认否定消息，不重新入队，若有死信队列则进入
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }


    /**
     * 延时队列
     */
    @RabbitListener(queues = QueueConst.DELAY_QUEUE)
    public void delayRec(Channel channel, Message message) throws IOException {

        log.warn("接收到延时消息：{}", message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 死信队列
     */
    @RabbitListener(queues = QueueConst.DLQ_QUEUE)
    public void dlqRec(Channel channel, Message message) throws IOException {

        log.warn("接收到死信消息：{}", message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
