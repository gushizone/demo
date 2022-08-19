package tk.gushizone.java.jdk8.functioninterface;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tk.gushizone.java.jdk8.entity.User;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-31 22:44
 */
@Slf4j
public class FunctionInterfaceTest {

    private static final List<User> LIST = Lists.newArrayList(new User("Foo", 16),
            new User("Bar", 18), new User("FooBar", 14));

    /**
     * function : 一个入参,一个出参
     */
    @Test
    public void function() {

        List<String> results = FunctionInterfaceUtils.function(LIST, User::getUsername);
        log.info(results.toString());
    }

    /**
     * consumer : 一个入参
     */
    @Test
    public void consumer () {

        StringBuilder result = new StringBuilder();
        FunctionInterfaceUtils.consumer(result::append, "b", "c", "a");
        log.info(result.toString());
    }

    /**
     * supplier : 一个出参
     */
    @Test
    public void supplier() {

        LinkedHashSet<User> results = FunctionInterfaceUtils.supplier(LIST, LinkedHashSet::new);
        log.info(results.toString());
    }


    /**
     * predicate : 一个入参 布尔返回值
     */
    @Test
    public void predicate() {

        List<User> results = FunctionInterfaceUtils.predicate(LIST, e -> e.getAge() >= 18);
        log.info(results.toString());
    }


    @Test
    public void test() {

        Supplier<?> s1 = () -> "s1";
        Supplier<?> s2 = () -> "s1";

    }
}
