package tk.gushizone.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gushizone@gmail.com
 * @date 2019-12-09 22:49
 */
@SpringBootApplication
@MapperScan(basePackages = {"tk.gushizone.**.dao"})
public class MyBatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisApplication.class);
    }
}
