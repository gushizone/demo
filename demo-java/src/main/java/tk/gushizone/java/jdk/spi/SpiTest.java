package tk.gushizone.java.jdk.spi;

import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * SPI（Service Provider Interface）：一种服务发现机制（类似于 Spring Bean 扫描），通过 META-INF/services 自动加载类
 * 常见应用如 JDBC，可参考 {@link java.sql.DriverManager}
 *
 * @author gushizone@gmail.com
 * @date 2021/12/6 2:50 下午
 */
public class SpiTest {

    public static void main(String[] args) {

        // 方式一
        Iterator<SpiService> providers = Service.providers(SpiService.class);
        while (providers.hasNext()) {
            SpiService item = providers.next();
            item.execute();
        }

        System.out.println("=========================================");

        // 方式二
        ServiceLoader<SpiService> serviceLoader = ServiceLoader.load(SpiService.class);
        Iterator<SpiService> spiIterator = serviceLoader.iterator();
        while (spiIterator.hasNext()) {
            SpiService item = spiIterator.next();
            item.execute();
        }


    }

}
