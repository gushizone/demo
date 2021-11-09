package tk.gushizone.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.gushizone.rabbitmq.constant.ExchangeConst;
import tk.gushizone.rabbitmq.constant.QueueConst;
import tk.gushizone.rabbitmq.constant.RabbitMqExtConst;
import tk.gushizone.rabbitmq.constant.RoutingKeyConst;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费者添加 Queue Bean，启动时会自动创建，且不需要设置 ignoreDeclarationExceptions = "true"
 *
 * @author gushizone@gmail.com
 * @date 2021/11/2 4:51 下午
 */
@Slf4j
@Configuration
public class MqConsumerConfig {

    private static final int TTL_EXPIRATION = 10_000;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

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
                .withArgument(RabbitMqExtConst.TTL_QUEUE_ARGUMENT, TTL_EXPIRATION)
                .build();
    }

    /*-------------------------- 延时队列 ------------------------------*/

    @Bean
    public Exchange delayExchange() {

        Map<String, Object> args = new HashMap<>();
        args.put(RabbitMqExtConst.DELAY_EXCHANGE_ARGUMENT, ExchangeTypes.TOPIC);
        return new CustomExchange(ExchangeConst.DELAY_EXCHANGE, RabbitMqExtConst.DELAY_EXCHANGE_TYPE, true, false, args);
    }

    @Bean
    public Queue delayQueue() {
        return QueueBuilder
                .durable(QueueConst.DELAY_QUEUE)
                .build();
    }

    @Bean
    public Binding delayBinding(Queue delayQueue, Exchange delayExchange) {
        return BindingBuilder
                .bind(delayQueue)
                .to(delayExchange)
                .with(RoutingKeyConst.DELAY_KEY)
                .noargs();
    }

    /*-------------------------- 死信队列 ------------------------------*/

    @Bean
    public Exchange dlxExchange() {
        return ExchangeBuilder
                .topicExchange(ExchangeConst.DLX_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder
                .durable(QueueConst.DLQ_QUEUE)
                .build();
    }

    @Bean
    public Binding dlkBinding(Queue dlqQueue, Exchange dlxExchange) {
        return BindingBuilder
                .bind(dlqQueue)
                .to(dlxExchange)
                .with(RoutingKeyConst.DLK_KEY)
                .noargs();
    }

    @Bean
    public Queue safeQueue() {
        return QueueBuilder
                .durable(QueueConst.SAFE_QUEUE)
                .withArgument(RabbitMqExtConst.DLX_QUEUE_ARGUMENT, ExchangeConst.DLX_EXCHANGE)
                .withArgument(RabbitMqExtConst.DLK_QUEUE_ARGUMENT, RoutingKeyConst.DLK_KEY)
                .build();
    }

}