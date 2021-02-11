package tk.gushizone.spring.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * BeanPostProcessor称为容器级生命周期接口方法，一般称为“后置处理器”
 *
 * @author gushizone@gmail.com
 * @date 2021/2/10 11:42 上午
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        super();
        System.out.println("BeanPostProcess - 构造器");
    }

    /**
     * @param bean     要处理的Bean对象
     * @param beanName bean的Name
     * @return 要处理的Bean对象
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcess - postProcessBeforeInitialization方法");
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcess - postProcessAfterInitialization方法");
        return null;
    }
}
