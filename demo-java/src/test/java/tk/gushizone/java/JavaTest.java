package tk.gushizone.java;

import org.junit.Test;

import java.util.Date;

/**
 * @author gushizone@gmail.com
 * @date 2020-08-23 18:41
 */
public class JavaTest {

    @Test
    public void test() throws InterruptedException {


        System.out.println(13&17);

        // 时间戳以1970年1月1日为起始，之所以多8小时，是因为当前在东八区
        // Thu Jan 01 08:00:00 CST 1970
        System.out.println(new Date(0));
    }




}
