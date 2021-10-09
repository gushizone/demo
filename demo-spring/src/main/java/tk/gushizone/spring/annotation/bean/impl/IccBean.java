package tk.gushizone.spring.annotation.bean.impl;

import tk.gushizone.spring.annotation.bean.IxxBean;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/9 3:48 下午
 */
public class IccBean implements IxxBean {

    @Override
    public String clazz() {
        return IccBean.class.getName();
    }
}
