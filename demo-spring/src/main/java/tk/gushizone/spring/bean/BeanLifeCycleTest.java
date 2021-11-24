package tk.gushizone.spring.bean;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tk.gushizone.spring.bean.lifecycle.BeanConfig;
import tk.gushizone.spring.bean.lifecycle.MyBean;

/**
 * bean 的生命周期
 *
 * @author gushizone@gmail.com
 * @date 2021/2/10 4:35 下午
 */
public class BeanLifeCycleTest {

    public static void main(String[] args) {

        System.out.println("容器初始化 - 开始");
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        System.out.println("容器初始化 - 结束");

        System.out.println("开始获取bean");
        MyBean bean = context.getBean("myBeanV1", MyBean.class);
        System.out.println(bean);

        System.out.println("开始关闭容器");
        context.registerShutdownHook();
    }
    /*
     *  容器初始化 - 开始
     *  BeanFactoryPostProcessor - 构造器
     *  BeanFactoryPostProcessor - postProcessBeanFactory方法
     *  BeanPostProcess - 构造器
     *  InstantiationAwareBeanPostProcessor - 构造器
     *  InstantiationAwareBeanPostProcessor - postProcessAfterInstantiation方法
     *  构造器
     *  填充属性name
     *  填充属性code
     *  InstantiationAwareBeanPostProcessorAdapter - postProcessProperties方法
     *  填充属性code
     *  BeanNameAware - setBeanName方法
     *  BeanFactoryAware - setBeanFactory方法
     *  BeanPostProcess - postProcessBeforeInitialization方法
     *  InitializingBean - afterPropertiesSet方法
     *  Bean - initMethod
     *  BeanPostProcess - postProcessAfterInitialization方法
     *  容器初始化 - 结束
     *  开始获取bean
     *  MyBean(name=Foo, code=222, beanName=MyBeanV1, beanFactory=org.springframework.beans.factory.support.DefaultListableBeanFactory@a7e666: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,beanConfig,beanPostProcessor,instantiationAwareBeanPostProcessorAdapter,beanFactoryPostProcessor,MyBeanV1]; root of factory hierarchy)
     *  开始关闭容器
     *  DisposableBean - destroy方法
     *  Bean - destroyMethod
     */


}
