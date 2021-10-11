package tk.gushizone.spring.aop;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/11 4:10 下午
 */
public class AspectTest {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AspectConfig.class);

        DemoService demoService = context.getBean(DemoService.class);
        demoService.go("abc");

        context.registerShutdownHook();
    }

}
