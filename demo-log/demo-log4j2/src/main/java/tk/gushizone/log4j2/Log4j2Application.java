package tk.gushizone.log4j2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * -Dlog4j2.formatMsgNoLookups=true
 *
 * @author gushizone@gmail.com
 * @date 2021/11/18 2:45 下午
 */
@Slf4j
@SpringBootApplication
public class Log4j2Application {

    public static void main(String[] args) {
        SpringApplication.run(Log4j2Application.class, args);
    }

    /**
     * log4j bug 检测
     */
//    public static void main(String[] args) {
//
////        String str = "123";
////        String str = "${java:os}";
//        String str = "${jndi:rmi://127.0.0.1:8080/test}";
//
//        log.info("test: {}", str);
//    }
}
