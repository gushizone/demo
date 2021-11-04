package tk.gushizone.rabbitmq.sample.work;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import tk.gushizone.rabbitmq.constant.QueueConst;
import tk.gushizone.rabbitmq.util.ConnectionUtil;

/**
 * 消费者1
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 21:55
 */
@Slf4j
public class Recv {

    public static void main(String[] argv) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QueueConst.WORK_QUEUE, false, false, false, null);
        // 设置每个消费者同时只能处理一条消息
        channel.basicQos(1);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                                       byte[] body) {

                channel.basicAck(envelope.getDeliveryTag(), false);

                log.warn("Consumer1 received : [{}]", new String(body));

                // 模拟完成任务的耗时
                Thread.sleep(100);
            }
        };

        channel.basicConsume(QueueConst.WORK_QUEUE, false, consumer);
    }
}