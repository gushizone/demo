package tk.gushizone.kafka;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.kafka.constant.TopicConst;
import tk.gushizone.kafka.entity.Item;
import tk.gushizone.kafka.sender.KafkaSender;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaProducerApplication.class)
public class KafkaProducerApplicationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaSender kafkaSender;

    @Test
    public void send() throws InterruptedException {

        Item item = new Item(1, "foo");

        kafkaSender.send(TopicConst.SPRING_KAFKA_TOPIC, JSONUtil.toJsonStr(item));

        // 确保异步回调
        Thread.sleep(10_000);
    }

}