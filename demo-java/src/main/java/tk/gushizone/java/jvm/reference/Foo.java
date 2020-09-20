package tk.gushizone.java.jvm.reference;

/**
 * @author gushizone@gmail.com
 * @date 2020-08-09 17:41
 */
public class Foo {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Foo gc ...");
    }
}
