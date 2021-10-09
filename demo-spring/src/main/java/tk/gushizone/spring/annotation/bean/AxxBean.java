package tk.gushizone.spring.annotation.bean;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * @see Component 什么组件，默认bean名称为类名（axxBean）
 *
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
