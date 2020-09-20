package tk.gushizone.java.common.lang;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;

/**
 * @author gushizone@gmail.com
 * @date 2020-07-03 11:43
 */
public class ClassTest {


    @Test
    public void classUtils(){
        //取得类名 Test
        System.out.println(ClassUtils.getShortClassName(Test.class));
        //取得其包名 org.junit
        System.out.println(ClassUtils.getPackageName(Test.class));

    }

}
