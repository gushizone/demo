package tk.gushizone.spring.bean.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.annotation.Bean;

/**
 * @author gushizone@gmail.com
 * @date 2021/2/10 4:39 下午
 */
@Slf4j
//@Configuration
public class BeanConfig {

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new MyBeanPostProcessor();
    }

    @Bean
    public InstantiationAwareBeanPostProcessorAdapter instantiationAwareBeanPostProcessorAdapter() {
        return new MyInstantiationAwareBeanPostProcessor();
    }

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new MyBeanFactoryPostProcessor();
    }

    @Bean(initMethod = "myInit", destroyMethod = "myDestroy")
    public MyBean myBeanV1() {
        MyBean myBean = new MyBean();
        myBean.setName("Foo");
        myBean.setCode(111);
        log.info("bean 创建成功: {}", myBean.hashCode());
        return myBean;
    }

//    @Bean(initMethod = "myInit", destroyMethod = "myDestroy")
    public MyBean myBeanV2() {
        MyBean myBean = new MyBean();
        myBean.setName("Bar");
        myBean.setCode(111);
        return myBean;
    }


}
