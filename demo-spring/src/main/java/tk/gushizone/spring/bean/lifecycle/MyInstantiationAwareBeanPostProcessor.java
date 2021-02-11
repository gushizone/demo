package tk.gushizone.spring.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 * @author gushizone@gmail.com
 * @date 2021/2/10 11:42 上午
 */
public class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out.println("InstantiationAwareBeanPostProcessor - 构造器");
    }

    /**
     * 实例化Bean之前调用
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor - postProcessAfterInstantiation方法");
        return null;
    }

    /**
     * 实例化Bean之后调用
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor - postProcessorAfterInitialization方法");
        return bean;
    }


    /**
     * 接口方法、设置某个属性时调用
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessorAdapter - postProcessProperties方法");
        return pvs;
    }
}
