package tk.gushizone.rabbitmq.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tk.gushizone.rabbitmq.constant.ExchangeConst;
import tk.gushizone.rabbitmq.constant.QueueConst;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-29 21:58
 */
@Slf4j
@Component
public class Listener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueConst.SPRING_QUEUE, durable = "true"),
            exchange = @Exchange(value = ExchangeConst.SPRING_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"#.#"}))
    public void listen(String msg) {

        log.warn("接收到消息：[{}]", msg);
    }
}
