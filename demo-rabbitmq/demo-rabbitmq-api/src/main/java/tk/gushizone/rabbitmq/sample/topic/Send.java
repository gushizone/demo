package tk.gushizone.rabbitmq.sample.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import tk.gushizone.rabbitmq.constant.ExchangeConst;
import tk.gushizone.rabbitmq.util.ConnectionUtil;

/**
 * 生产者，模拟为商品服务
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 23:04
 */
@Slf4j
public class Send {

    public static void main(String[] argv) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明 exchange，指定类型为topic (durable: 是否持久化)
        channel.exchangeDeclare(ExchangeConst.TOPIC_EXCHANGE, "topic", true);
        // 消息内容
        String msg = "新增商品 : id = 1001";
        // 发送消息，并且指定routing key 为：insert ,代表新增商品 (消息持久化：MessageProperties.PERSISTENT_TEXT_PLAIN)
        channel.basicPublish(ExchangeConst.TOPIC_EXCHANGE, "item.insert", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        log.warn("Producer sent [{}]", msg);

        channel.close();
        connection.close();
    }
}