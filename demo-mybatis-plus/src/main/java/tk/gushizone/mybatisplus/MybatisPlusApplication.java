package tk.gushizone.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gushizone
 * @date 2022/8/19 11:32
 */
@SpringBootApplication
@MapperScan("tk.gushizone.mybatisplus.**.mapper")
public class MybatisPlusApplication {


    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }


}
