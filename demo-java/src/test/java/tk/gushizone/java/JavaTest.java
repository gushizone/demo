package tk.gushizone.java;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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


    /**
     * 由于泛型擦除,使得Generic无法获取自己的Generic的Type类型。
     * 实际上BadClass()实例化以后Class里面就不包括T的信息了，对于Class而言T已经被擦拭为Object，
     * 而真正的T参数被转到使用T的方法（或者变量声明或者其它使用T的地方）里面（如果没有那就没有存根），
     * 所以无法反射到T的具体类别，也就无法得到T.class。
     * 而getGenericSuperclass()是Generic继承的特例，对于这种情况子类会保存父类的Generic参数类型，
     * 返回一个ParameterizedType，这时可以获取到父类的T.class了，这也正是子类确定应该继承什么T的方法
     */
    @Test
    public void test1() {

        List<String> list = Lists.newArrayList();
        System.out.println(list.getClass().getGenericSuperclass());
        // java.util.AbstractList<E>

        DemoList demoList = new DemoList();
        System.out.println(demoList.getClass().getGenericSuperclass());
        // java.util.ArrayList<java.lang.Integer>

    }

    public static class DemoList extends ArrayList<Integer> {}

    @Test
    public void test2() {
        Producer kaptchaProducer = initKaptcha();
        String text = kaptchaProducer.createText();
        System.out.println("==========text : " + text);;
        BufferedImage image = kaptchaProducer.createImage(text);
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64Image = Base64.encode(out.toByteArray());
        System.out.println(base64Image);
    }

    private Producer initKaptcha() {
        DefaultKaptcha dk = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.put("kaptcha.border", "yes");
        properties.put("kaptcha.border.color", "105,179,90");
        properties.put("kaptcha.textproducer.font.color", "blue");
        properties.put("kaptcha.image.width", "125");
        properties.put("kaptcha.image.height", "45");
        properties.put("kaptcha.textproducer.font.size", "30");
        properties.put("kaptcha.session.key", "code");
        properties.put("kaptcha.textproducer.char.length", "4");
//        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        dk.setConfig(config);
        return dk;
    }

    @SneakyThrows
    @Test
    public void testss() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        String[] fonts = ge.getAvailableFontFamilyNames();
        System.out.println(JSONUtil.toJsonStr(fonts));

    }

}
