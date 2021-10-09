package tk.gushizone.spring.annotation.bean.impl;

import org.springframework.stereotype.Component;
import tk.gushizone.spring.annotation.bean.IxxBean;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/9 3:48 下午
 */
@Component
public class IbbBean implements IxxBean {

    @Override
    public String clazz() {
        return IbbBean.class.getName();
    }
}
