package tk.gushizone.spring.annotation;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/3 12:55 上午
 */
public class AnnotationTest {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfig.class);
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));

        context.registerShutdownHook();
    }


}
