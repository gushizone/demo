package tk.gushizone.rabbitmq.sample.work;

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
 * 消费者 2
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 22:04
 */
@Slf4j
public class Recv2 {

    public static void main(String[] argv) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QueueConst.WORK_QUEUE, false, false, false, null);
        channel.basicQos(1);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                                       byte[] body) throws IOException {

                channel.basicAck(envelope.getDeliveryTag(), false);

                log.warn("Consumer2 received : [{}]", new String(body));
            }
        };

        channel.basicConsume(QueueConst.WORK_QUEUE, false, consumer);
    }
}