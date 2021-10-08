package tk.gushizone.spring.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

/**
 * @author gushizone@gmail.com
 * @date 2021/9/2 4:49 下午
 */
@Slf4j
@Configuration
public class ScheduleConfig {

    int count = 0;

    @Scheduled(cron = "0 0/1 * * * *")
    public void ding() {

        log.warn("ding: spring 已启动： {} min", count++);

    }

    @PostConstruct
    public void init() {
        count++;
    }



}
