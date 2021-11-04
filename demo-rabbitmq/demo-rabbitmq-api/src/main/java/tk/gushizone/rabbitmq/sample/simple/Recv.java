package tk.gushizone.rabbitmq.sample.simple;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import tk.gushizone.rabbitmq.constant.QueueConst;
import tk.gushizone.rabbitmq.util.ConnectionUtil;


/**
 * 消费者
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 19:35
 */
@Slf4j
public class Recv {

    public static void main(String[] argv) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QueueConst.SIMPLE_QUEUE, false, false, false, null);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             * 消息接收处理器（回调函数）
             * @param body 消息体
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                                       byte[] body) {

                log.warn("Consumer received : [{}]", new String(body));
            }
        };
        // 监听队列，自动ACK
        channel.basicConsume(QueueConst.SIMPLE_QUEUE, true, consumer);
    }
}