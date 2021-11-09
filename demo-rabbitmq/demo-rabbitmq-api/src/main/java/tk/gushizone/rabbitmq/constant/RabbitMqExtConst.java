package tk.gushizone.rabbitmq.constant;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/9 11:51 上午
 */
public interface RabbitMqExtConst {

    String TTL_QUEUE_ARGUMENT = "x-message-ttl";

    String DELAY_EXCHANGE_TYPE = "x-delayed-message";
    String DELAY_EXCHANGE_ARGUMENT = "x-delayed-type";

    String DLX_QUEUE_ARGUMENT = "x-dead-letter-exchange";
    String DLK_QUEUE_ARGUMENT = "x-dead-letter-routing-key";

}
