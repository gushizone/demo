package tk.gushizone.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 17:44
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"tk.gushizone.**.dao"})
public class SpringDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoApplication.class);
    }
}
