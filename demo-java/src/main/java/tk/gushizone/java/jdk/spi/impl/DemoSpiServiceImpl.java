package tk.gushizone.java.jdk.spi.impl;

import tk.gushizone.java.jdk.spi.SpiService;

/**
 * @author gushizone@gmail.com
 * @date 2021/12/6 2:46 下午
 */
public class DemoSpiServiceImpl implements SpiService {

    @Override
    public void execute() {
        System.out.println("DEMO SPI");
    }
}
