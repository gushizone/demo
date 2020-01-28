package tk.gushizone.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author gushizone@gmail.com
 * @date 2020-01-28 19:34
 */
public class ConnectionUtil {

    /**
     * 建立与RabbitMQ的连接
     */
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/test");
        factory.setUsername("test");
        factory.setPassword("test");
        // 通过工程获取连接
        return factory.newConnection();
    }

}
