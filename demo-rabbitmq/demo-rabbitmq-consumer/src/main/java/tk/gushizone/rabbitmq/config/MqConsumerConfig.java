package tk.gushizone.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.gushizone.rabbitmq.constant.QueueConst;

/**
 * 消费者添加 Queue Bean，启动时会自动创建，且不需要设置 ignoreDeclarationExceptions = "true"
 *
 * @author gushizone@gmail.com
 * @date 2021/11/2 4:51 下午
 */
@Slf4j
@Configuration
public class MqConsumerConfig {

    private static final String TTL_ARGUMENT = "x-message-ttl";
    private static final int TTL_EXPIRATION = 10_000;

    /**
     * 声明并自动创建 queue
     * 如果不申明
     */
    @Bean
    public Queue demoQueue() {
        return new Queue(QueueConst.DEMO_QUEUE, true);
    }


    /**
     * ttl 队列（TTL必需在创建是初始化）
     */
    @Bean
    public Queue ttlQueue() {
        return QueueBuilder
                .durable(QueueConst.TTL_QUEUE)
//                消息有效期
                .withArgument(TTL_ARGUMENT, TTL_EXPIRATION)
                .build();
    }
}