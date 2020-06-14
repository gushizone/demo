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

import java.util.List;

/**
 * rabbitmq 默认使用 java 的序列化机制，
 * 故java的信息可以被正常传递，如枚举类型等
 *
 * @author gushizone@gmail.com
 * @date 2020-01-29 21:58
 */
@Slf4j
@Component
public class Listener {

    /**
     * 接收单个对象 - 必须类型相同
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueConst.SPRING_QUEUE_1, durable = "true"),
            exchange = @Exchange(value = ExchangeConst.SPRING_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"o.#"}))
    public void listens(Item msg) {

        log.warn("接收到消息：[{}]", msg);
    }

    /**
     * 接收集合 （因为泛型擦除，只在类型转换时才会报错）
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueConst.SPRING_QUEUE_2, durable = "true"),
            exchange = @Exchange(value = ExchangeConst.SPRING_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"l.#"}))
    public void listen(List<String> msgs) {

        log.warn("接收到消息s：[{}]", msgs);
    }


}
