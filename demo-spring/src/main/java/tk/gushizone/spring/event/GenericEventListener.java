package tk.gushizone.spring.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author gushizone
 * @date 2022/10/17 14:58
 */
@Component
public class GenericEventListener {

    private Object mqTemplate;

    //    @Async
//    @Order
    @EventListener
    public void handle(GenericEvent<Product> event) {

        Product source = (Product) event.getSource();
        System.out.println(source);
    }


    @EventListener
    public void handleV0(GenericEvent<Object> event) {

        Object source = event.getSource();
        System.out.println(source);
    }
}
