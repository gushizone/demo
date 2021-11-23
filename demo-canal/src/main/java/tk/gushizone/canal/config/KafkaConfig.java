package tk.gushizone.canal.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.gushizone.canal.constant.TopicConst;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/17 3:57 下午
 */
@Configuration
public class KafkaConfig {


    /**
     * canal.mq.dynamicTopic=.*\\..*
     * 对应 数据库名_表名
     */
    @Bean
    public NewTopic demoItem() {
        // 高版本中有 TopicBuilder
        return new NewTopic(TopicConst.DEMO_ITEM, 2, (short) 2);
    }
}
