package tk.gushizone.java.jvm;

import org.junit.Test;

/**
 * @author gushizone
 * @date 2023/1/4 14:06
 */
public class JvmTest {

    @Test
    public void properties() {
        System.getProperties().forEach((k, v) -> System.out.println(k + " = " + v));
    }

    @Test
    public void env() {
        System.getenv().forEach((k, v) -> System.out.println(k + " = " + v));
    }

}
