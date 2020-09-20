package tk.gushizone.java.common.lang;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-03 22:26
 */
public class StringTest {


    /**
     * commons-lang3
     * (commons-lang3是对commons-lang的升级，需要JDK5以上。)
     * <p>
     * 一个Java实用程序类包，用于java.lang层次结构中的类
     */
    @Test
    public void stringUtils() {

        String[] strArr = {"a", "b", "c"};

        // 检查String是否包含文本
        System.out.println(StringUtils.isEmpty(" ")); // false
        System.out.println(StringUtils.isBlank(" ")); // true

        // 数组转字符串，使用分隔符分割
        System.out.println(StringUtils.join(strArr, ",")); // a,b,c

        //首字母大写
        System.out.println(StringUtils.capitalize("abc")); // Abc
        //删除所有空格
        System.out.println(StringUtils.deleteWhitespace("  a bc  ")); // abc

        // --- 确保null安全 (基本方法都确保了null判断) ---
        //判断是否包含这个字符
        System.out.println(StringUtils.contains("abc", "bc")); // true

    }
}
