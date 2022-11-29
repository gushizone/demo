package tk.gushizone.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author gushizone
 * @date 2022/10/17 14:49
 */
public class ProductEvent extends ApplicationEvent {

    public ProductEvent(Product source) {
        super(source);
    }
}
