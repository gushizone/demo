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

    /**
     * fixedDelay : 固定延迟，本次任务结束和下次任务开始的间隔时间，任务不会重叠。
     * fixedRate : 固定频率，固定时间开始下次任务，上次任务可能没结束，任务可能重叠。
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void ding() {

        log.warn("ding: spring 已启动： {} min", count++);

    }

    @PostConstruct
    public void init() {
        count++;
    }



}
