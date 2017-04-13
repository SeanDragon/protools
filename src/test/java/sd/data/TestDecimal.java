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
        System.out.println(add.sqrt2(999));
        System.err.println("1----------" + (System.currentTimeMillis() - l));
        l = System.currentTimeMillis();
        add = new DecimalPlus("15955555544444444444444444444444444444444444555555555222222222222222222555555555555555555522222222222222");
        double a = add.toDouble();
        System.out.println(Math.sqrt(a));
        System.err.println("2----------" + (System.currentTimeMillis() - l));
    }

    @Test
    public void test3() {
        DecimalPlus add = new DecimalPlus(6.25 * 6.25 * 6.25);
        add = new DecimalPlus(6.55 * 6.55 * 6.55);
        System.out.println(add.toRealString());
        System.out.println(add.sqrtN(3).toRealString());
        //System.out.println(add.sqrt2(200).toRealString());

        //System.out.println(new BigDecimal("2.00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000").subtract(new BigDecimal(2)).doubleValue() == 0);
    }

    @Test
    public void test4() {
        //long num = 27L;
        //int i = ToolDecimal.numberOfLeadingZeros(num);
        //System.out.println(63 - i);
        //System.out.println(Math.log(num) / Math.log(2));
        double d1 = 64 * 64 * 64D;
        double d2 = 64 * 64 * 64D;

        double v = Math.log(d1) / Math.log(3);
        double pow = Math.pow(d2, 1D / 3);
        System.out.println(v);
        System.out.println(pow);
    }

    @Test
    public void test5() {
    }
}
