package tk.gushizone.spring.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 工厂后置处理器  工厂后置处理器也是容器级的。在应用上下文装配配置文件之后立即调用
 *
 * @author gushizone@gmail.com
 * @date 2021/2/10 11:42 上午
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("BeanFactoryPostProcessor - 构造器");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        System.out.println("BeanFactoryPostProcessor - postProcessBeanFactory方法");
        // BeanDefinition
        BeanDefinition person = beanFactory.getBeanDefinition("MyBeanV1");
        person.getPropertyValues().addPropertyValue("code", "222");
    }
}
