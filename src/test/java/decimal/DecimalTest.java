package decimal;

import org.junit.Test;
import pro.tools.data.decimal.DecimalPlus;
import pro.tools.decimal;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * Created by tuhao on 2017/4/13.
 */

public class DecimalTest {

    @Test
    public void decimalTest() {
        BigDecimal bigDecimal = decimal.get(1.52D);
        bigDecimal = bigDecimal.negate();
        System.out.println(bigDecimal.doubleValue());
        bigDecimal = bigDecimal.negate(MathContext.DECIMAL32);
        System.out.println(bigDecimal.doubleValue());
    }

    @Test
    public void test1() {
        DecimalPlus decimalPlus = new DecimalPlus("11");
        decimalPlus.add(1).sub(1).mul(1).div(1);
        System.out.println(decimalPlus.toRealString());
        System.out.println(decimalPlus.getDivGetInteger(2));
        System.out.println(decimalPlus.toRealString());
        System.out.println(decimalPlus.getRemainder(2));
        System.out.println(decimalPlus.toRealString());
    }

    @Test
    public void tes2() {
        DecimalPlus add = new DecimalPlus("159753");
        //System.out.println(add.toMoney());
        long l = System.currentTimeMillis();
        System.out.println(add.sqrt2(99999999).toRealString());
        System.err.println("1----------" + (System.currentTimeMillis() - l));
        l = System.currentTimeMillis();
        add = new DecimalPlus("159753");
        double a = add.toDouble();
        System.out.println(Math.sqrt(a));
        System.err.println("2----------" + (System.currentTimeMillis() - l));
    }

    @Test
    public void test3() {
        DecimalPlus decimalPlus = new DecimalPlus(555544.23252);
        System.out.println(decimalPlus.toDouble());
        System.out.println(decimalPlus.toDouble(2, RoundingMode.HALF_EVEN));
        System.out.println(decimalPlus.toDouble(3, RoundingMode.HALF_EVEN));
    }
}
