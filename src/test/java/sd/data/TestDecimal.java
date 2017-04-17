package sd.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pro.tools.data.decimal.Decimal;

import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * @author SeanDragon
 *         Create By 2017-04-13 14:26
 */
public class TestDecimal {

    private long begin;

    @Before
    public void b() {
        begin = System.currentTimeMillis();
    }

    @Test
    public void test1() {
        Decimal decimal = new Decimal("1.123456");
        System.out.println(decimal);
        decimal = new Decimal(1.123456F);
        System.out.println(decimal);
        decimal = new Decimal(112345678910111213L);
        System.out.println(decimal);
        decimal = new Decimal(1123456789D);
        System.out.println(decimal);
        decimal = new Decimal(new BigInteger("1123456789123456789123456789"));
        System.out.println(decimal);

    }

    @Test
    public void test2() {
        Decimal add = new Decimal("15955555544444444444444444444444444444444444555555555222222222222222222555555555555555555522222222222222");
        //System.out.println(add.moneyValue());
        long l = System.currentTimeMillis();
        System.out.println(add.sqrt2(999));
        System.err.println("1----------" + (System.currentTimeMillis() - l));
        l = System.currentTimeMillis();
        add = new Decimal("15955555544444444444444444444444444444444444555555555222222222222222222555555555555555555522222222222222");
        double a = add.doubleValue();
        System.out.println(Math.sqrt(a));
        System.err.println("2----------" + (System.currentTimeMillis() - l));
    }

    @Test
    public void test3() {
        Decimal add = new Decimal(6.25 * 6.25 * 6.25);
        add = new Decimal(6.55 * 6.55 * 6.55);
        System.out.println(add.fullStrValue());
        System.out.println(add.sqrtN(3).fullStrValue());
        //System.out.println(add.sqrt2(200).fullStrValue());

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

        Decimal instance = Decimal.instance(1.00D);
        instance.add(2)
                //        .sub(2)
                //        .mul(2)
                //        .div(2)
                .pow(500)
                .sqrtN(5000)
        //        .sub(0.02)
        ;
        System.out.println(instance.fullStrValue());
        System.out.println(instance.moneyValue());
        System.out.println(instance.longValue());
        System.out.println(instance.intValue());
        System.out.println(instance.shortValue());
        System.out.println(instance.byteValue());
        System.out.println(instance.floatValue());
        System.out.println(instance.doubleValue(200, RoundingMode.HALF_EVEN));
        System.out.println(instance.moneyStrValue());
    }

    @After
    public void a() {
        long cost = System.currentTimeMillis() - begin;
        System.out.println("test cost time is " + cost);
    }
}
