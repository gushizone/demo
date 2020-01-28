package tk.gushizone.rabbitmq.sample.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import tk.gushizone.rabbitmq.constant.QueueConst;
import tk.gushizone.rabbitmq.util.ConnectionUtil;

/**
 * 生产者
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 21:53
 */
@Slf4j
public class Send {

    public static void main(String[] argv) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QueueConst.WORK_QUEUE, false, false, false, null);

        // 多次发布任务
        for (int i = 0; i < 10; i++) {

            String msg = "task .. " + i;
            channel.basicPublish("", QueueConst.WORK_QUEUE, null, msg.getBytes());
            log.warn("Producer sent [{}]", msg);

            Thread.sleep(i * 2);
        }

        channel.close();
        connection.close();
    }
}

