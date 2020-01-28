package tk.gushizone.rabbitmq.sample.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import tk.gushizone.rabbitmq.constant.ExchangeConst;
import tk.gushizone.rabbitmq.util.ConnectionUtil;

/**
 * exchange 交换机只负责转发，不会储存信息
 * TODO 必须先启动生成者创建交换机，但因为没有队列绑定，会丢失数据
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 22:20
 */
@Slf4j
public class Send {

    public static void main(String[] argv) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        
        // 声明exchange，指定类型为fanout
        channel.exchangeDeclare(ExchangeConst.FANOUT_EXCHANGE, "fanout");
        
        String msg = "Hello World";
        // 发布消息到Exchange
        channel.basicPublish(ExchangeConst.FANOUT_EXCHANGE, "", null, msg.getBytes());
        log.warn("Producer sent [{}]", msg);

        channel.close();
        connection.close();
    }
}