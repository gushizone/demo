package tk.gushizone.java.jdk.arithmetic;

import org.junit.Test;

import java.math.BigDecimal;

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


}
