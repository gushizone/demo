package tk.gushizone.rabbitmq.sample.topic;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import tk.gushizone.rabbitmq.constant.ExchangeConst;
import tk.gushizone.rabbitmq.constant.QueueConst;
import tk.gushizone.rabbitmq.util.ConnectionUtil;

/**
 * 消费者 2
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 23:14
 */
@Slf4j
public class Recv2 {

    public static void main(String[] argv) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列 (durable: 是否持久化)  
        channel.queueDeclare(QueueConst.TOPIC_QUEUE_2, true, false, false, null);
        // 绑定队列到交换机，同时指定需要订阅的routing key。订阅 insert、update、delete
        channel.queueBind(QueueConst.TOPIC_QUEUE_2, ExchangeConst.TOPIC_EXCHANGE, "item.*");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                                       byte[] body) {
                log.warn("Consumer2 received [{}]", new String(body));
            }
        };

        channel.basicConsume(QueueConst.TOPIC_QUEUE_2, true, consumer);
    }
}