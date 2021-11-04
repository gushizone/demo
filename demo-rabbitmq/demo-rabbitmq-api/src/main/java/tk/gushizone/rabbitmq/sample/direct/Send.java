package tk.gushizone.rabbitmq.sample.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import tk.gushizone.rabbitmq.constant.ExchangeConst;
import tk.gushizone.rabbitmq.util.ConnectionUtil;

/**
 * 生产者，模拟为商品服务
 * Direct ： 根据 RoutingKey 进行消息订阅
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 22:43
 */
@Slf4j
public class Send {

    public static void main(String[] argv) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange，指定类型为direct
        channel.exchangeDeclare(ExchangeConst.DIRECT_EXCHANGE, "direct");
        // 消息内容
        String msg = "商品删除， id = 1001";
        // 发送消息，并且指定routing key 为：insert ,代表新增商品
        channel.basicPublish(ExchangeConst.DIRECT_EXCHANGE, "delete", null, msg.getBytes());
        log.warn("Producer sent [{}]", msg);

        channel.close();
        connection.close();
    }
}