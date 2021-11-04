package tk.gushizone.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.gushizone.rabbitmq.constant.QueueConst;

import javax.annotation.PostConstruct;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/2 4:51 下午
 */
@Slf4j
@Configuration
public class MqProducerConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 生产者配置
     */
    @PostConstruct
    public RabbitTemplate init() {

        // 消息确认回调：ack 表示消息是否到达 exchange, 本质是一个异步监听等过程
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功, correlationData : {}", correlationData);
                return;
            }

            log.error("消息发送失败, cause : {}", cause);
        });

        // 发送回调：消息未到达队列时触发, 本质是一个异步监听等过程
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {

            // ...
            log.error("不可路由消息，消息主体: {}, 应答码: {}, 描述: {}, exchange: {}, routingKey: {}", message, replyCode, replyText, exchange, routingKey);
        });

        // 将 CorrelationId 放入 MessageProperties 中，消费者才能拿到，否则为null，此方法也是依赖配置
        // CorrelationId 用于标识消息，用于幂等/重复等问题处理，推荐定义统一消息体来传递
        rabbitTemplate.setCorrelationDataPostProcessor((message, correlationData) -> {
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setCorrelationId(correlationData != null ? correlationData.getId() : null);
            return correlationData;
        });

        return rabbitTemplate;
    }
}