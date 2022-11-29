package tk.gushizone.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.gushizone.kafka.constant.TopicConst;

/**
 * todo 会自动创建 topic
 *
 * @author gushizone@gmail.com
 * @date 2021/11/17 3:57 下午
 */
//@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic springKafkaTopic() {
        // 高版本中有 TopicBuilder
        return new NewTopic(TopicConst.SPRING_KAFKA_TOPIC, 10, (short) 2);
    }
}
