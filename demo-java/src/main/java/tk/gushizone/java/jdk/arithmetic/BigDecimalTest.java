package tk.gushizone.java.jdk.arithmetic;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-02 23:56
 */
public class BigDecimalTest {


    @Test
    public void eqOrCompare() {

        BigDecimal zero = new BigDecimal("0.0");
        if (BigDecimal.ZERO.equals(zero)) {
            System.out.println("equals");
        }
        if (BigDecimal.ZERO.compareTo(zero) == 0) {
            System.out.println("compareTo");
        }
    }

    @Test
    public void test() {
        BigDecimal b24 = new BigDecimal("24");
        BigDecimal b8 = new BigDecimal("8");

        BigDecimal b12 = new BigDecimal("12");

        // 3.9996
        System.out.println(b12.multiply(b8.divide(b24, 8, RoundingMode.DOWN)));
        // 4.0000 : 高精度计算 低精度保留
        System.out.println(b12.multiply(b8.divide(b24, 8, RoundingMode.DOWN)).setScale(4, RoundingMode.HALF_UP));
        // 4.0000 : 先乘后除
        System.out.println(b12.multiply(b8).divide(b24, 4, RoundingMode.HALF_UP));
    }


}
