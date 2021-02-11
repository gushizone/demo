package tk.gushizone.spring.bean.lifecycle;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * BeanNameAware、BeanFactoryAware、InitializingBean 和 DisposableBean 这些接口属于Bean级别声明周期接口的方法
 *
 * @author gushizone@gmail.com
 * @date 2021/2/10 11:42 上午
 */
@Getter
@ToString
public class MyBean implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean {

    private String name;
    private Integer code;

    private String beanName;
    private BeanFactory beanFactory;

    public MyBean() {
        System.out.println("构造器");
    }

    public void setName(String name) {
        System.out.println("填充属性name");
        this.name = name;
    }

    public void setCode(Integer code) {
        System.out.println("填充属性code");
        this.code = code;
    }


    /**
     * 这是BeanNameAware接口方法
     */
    @Override
    public void setBeanName(String beanName) {
        System.out.println("BeanNameAware - setBeanName方法");
        this.beanName = beanName;
    }

    /**
     * BeanFactoryAware接口方法
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware - setBeanFactory方法");
        this.beanFactory = beanFactory;
    }

    /**
     * 这是InitializingBean接口方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean - afterPropertiesSet方法");
    }


    /**
     * DisposableBean接口 - destroy
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean - destroy方法");
    }

    /**
     * Bean - initMethod
     */
    private void myInit() {
        System.out.println("Bean - initMethod");
    }

    /**
     * Bean - destroyMethod
     */
    public void myDestroy() {
        System.out.println("Bean - destroyMethod");
    }
}
