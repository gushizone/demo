package tk.gushizone.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 17:44
 */
@SpringBootApplication
@MapperScan(basePackages = {"tk.gushizone.**.dao"})
public class SpringDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplication.class);
    }
}
