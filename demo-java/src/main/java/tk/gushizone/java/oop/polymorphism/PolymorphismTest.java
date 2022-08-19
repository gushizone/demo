package tk.gushizone.java.oop.polymorphism;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author gushizone
 * @date 2022/5/16 15:31
 */
public class PolymorphismTest {


    @Test
    public void test() {

        List<BaseBird> list = Lists.newArrayList();

        list.add(new BaseBird());
        list.add(new BirdV1());

        for (BaseBird bird : list) {
            bird.chirp();
        }
    }

}
