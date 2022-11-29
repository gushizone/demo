package tk.gushizone.spring.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author gushizone
 * @date 2022/10/17 14:58
 */
@Component
public class ProductEventListener {

    private Object mqTemplate;

//    @Async
//    @Order
    @EventListener(ProductEvent.class)
    public void handle(ProductEvent event) {

        Product source = (Product) event.getSource();
        System.out.println(source);
    }


}
