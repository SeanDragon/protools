package sd.data;

import org.junit.Test;
import pro.tools.data.decimal.DecimalPlus;

import java.math.BigInteger;

/**
 * @author SeanDragon
 *         Create By 2017-04-13 14:26
 */
public class TestDecimal {
    @Test
    public void test1() {
        DecimalPlus decimalPlus = new DecimalPlus("1.123456");
        System.out.println(decimalPlus);
        decimalPlus = new DecimalPlus(1.123456F);
        System.out.println(decimalPlus);
        decimalPlus = new DecimalPlus(112345678910111213L);
        System.out.println(decimalPlus);
        decimalPlus = new DecimalPlus(1123456789D);
        System.out.println(decimalPlus);
        decimalPlus = new DecimalPlus(new BigInteger("1123456789123456789123456789"));
        System.out.println(decimalPlus);

    }

    @Test
    public void test2() {
        DecimalPlus add = new DecimalPlus("15955555544444444444444444444444444444444444555555555222222222222222222555555555555555555522222222222222");
        //System.out.println(add.toMoney());
        long l = System.currentTimeMillis();
        System.out.println(add.sqrt());
        System.err.println("1----------" + (System.currentTimeMillis() - l));
        l = System.currentTimeMillis();
        add = new DecimalPlus("15955555544444444444444444444444444444444444555555555222222222222222222555555555555555555522222222222222");
        double a = add.toDouble();
        System.out.println(Math.sqrt(a));
        System.err.println("2----------" + (System.currentTimeMillis() - l));
    }

    @Test
    public void test3() {
        DecimalPlus add = new DecimalPlus("2.5");
        System.out.println(add.toRealString());
        System.out.println(add.pow(2));
        System.out.println(add.sqrt());
    }
}
