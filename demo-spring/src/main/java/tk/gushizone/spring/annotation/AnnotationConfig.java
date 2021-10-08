package tk.gushizone.spring.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/3 12:37 上午
 */
@Configuration
@ComponentScan
public class AnnotationConfig {

    @Autowired
    public AxxBean a;

    @Resource
    private AxxBean axxBean;


    @Bean
    @Required
    public BxxBean b() {
        return new BxxBean();
    }



}
