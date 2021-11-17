package tk.gushizone.kafka.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/17 4:06 下午
 */
@Slf4j
@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String Object) {

        ListenableFuture<? extends SendResult<String, String>> future = kafkaTemplate.send(topic, Object);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onFailure(Throwable ex) {

                log.error("发送消息失败: {}", ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {

                log.info("发送消息成功: {}", result);
            }
        });
    }
}
