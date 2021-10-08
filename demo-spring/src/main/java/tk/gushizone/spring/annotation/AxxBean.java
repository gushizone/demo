package tk.gushizone.spring.annotation;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/3 12:35 上午
 */
@Component
public class AxxBean implements BeanNameAware {

    private String name;

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }
}
