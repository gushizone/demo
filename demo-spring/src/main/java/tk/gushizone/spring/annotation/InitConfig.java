package tk.gushizone.spring.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import tk.gushizone.spring.annotation.bean.IxxBean;
import tk.gushizone.spring.annotation.bean.impl.IccBean;
import tk.gushizone.spring.annotation.bean.impl.IddBean;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/9 3:45 下午
 */
@PropertySource(value = "classpath:application.properties")
public class InitConfig {


    /**
     * @see Primary 标识最主要的bean
     * @see DependsOn 指定bean的依赖前提，即说明类初始化顺序
     */
    @Bean
    @Primary
    @DependsOn("iddBean")
    public IxxBean iccBean() {
        System.out.println("init IccBean");
        return new IccBean();
    }

    /**
     * @see Order 指定同类型的注册顺序，不会影响初始化顺序
     */
    @Bean
    @Order(-1)
    public IxxBean iddBean() {
        System.out.println("init IddBean");
        return new IddBean();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
