package tk.gushizone.rabbitmq.sample.simple;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import tk.gushizone.rabbitmq.constant.QueueConst;
import tk.gushizone.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * 消费者,手动ACK
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 21:26
 */
@Slf4j
public class Recv2 {

    public static void main(String[] argv) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();


        channel.queueDeclare(QueueConst.SIMPLE_QUEUE, false, false, false, null);
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                                       byte[] body) throws IOException {
                // 手动ACK
                channel.basicAck(envelope.getDeliveryTag(), false);

                log.warn("Consumer received : [{}]", new String(body));
            }
        };
        // 监听队列，手动ACK
        channel.basicConsume(QueueConst.SIMPLE_QUEUE, false, consumer);
    }
}