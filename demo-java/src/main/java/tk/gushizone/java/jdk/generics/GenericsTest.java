package tk.gushizone.java.jdk.generics;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gushizone@gmail.com
 * @date 2021/6/14 1:51 上午
 */
@Slf4j
public class GenericsTest {

    public void test() {

        // E - Element : 集合中的元素
        List<String> elements = new ArrayList<>();

        // K - key (键)
        // V - value (值)
        Map<String, Object> map = new HashMap<>();

        // T - Type（Java类）U S

        // R - Return

        // N - Number（数值类型）

        // ? - 不确定的java类型
    }

    /**
     * PECS（Producer Extends Consumer Super）原则
     */
    public void pecs() {

        // ? extends T
        Container<? extends Date> ec = new Container<>();
        // 可以取
        long time = ec.getFoo().getTime();
        // 不能放
//        ec.setFoo(new Time(0));



        // ? super T
        Container<? super Time> sc = new Container<>();
        // 可以放
        sc.setFoo(new Time(0));
        // 不能取
        Object foo = sc.getFoo();
//        Time time = sc.getFoo();
    }

    @Data
    public static class Container<T> {

        private T foo;
    }

}
