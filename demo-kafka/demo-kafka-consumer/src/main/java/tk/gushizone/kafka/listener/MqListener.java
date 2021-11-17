package tk.gushizone.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tk.gushizone.kafka.constant.GroupConst;
import tk.gushizone.kafka.constant.TopicConst;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/17 3:22 下午
 */
@Slf4j
@Component
public class MqListener {


    @KafkaListener(groupId = GroupConst.SPRING_KAFKA_GROUP, topics = TopicConst.SPRING_KAFKA_TOPIC)
    public void rec(ConsumerRecord<String, ?> record,
                    Acknowledgment acknowledgment,
                    Consumer<?, ?> consumer) {
        log.info("接收消息: {}", record.value());

        // 手工签收（包含了 addOffset 等）
        acknowledgment.acknowledge();
    }


}
