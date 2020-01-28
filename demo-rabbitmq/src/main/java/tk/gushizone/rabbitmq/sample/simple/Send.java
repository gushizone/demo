package tk.gushizone.rabbitmq.sample.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import tk.gushizone.rabbitmq.constant.QueueConst;
import tk.gushizone.rabbitmq.util.ConnectionUtil;


/**
 * 生产者
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 21:27
 */
@Slf4j
public class Send {

    public static void main(String[] argv) throws Exception {

        // 获取连接并创建通道（使用通道才能完成消息相关的操作）
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明（创建）队列
        channel.queueDeclare(QueueConst.SIMPLE_QUEUE, false, false, false, null);
        // 消息内容
        String msg = "Hello World!";
        // 向指定的队列中发送消息
        channel.basicPublish("", QueueConst.SIMPLE_QUEUE, null, msg.getBytes());

        log.warn("Producer sent : [{}]", msg);

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}