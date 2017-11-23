package decimal;

import org.junit.Test;
import pro.tools.data.decimal.Decimal;

import java.math.RoundingMode;

public class TestDecimal {
    @Test
    public void test1() {
        Decimal instance = Decimal.instance(1).div(3);
        String s = instance.fullStrValue();
        instance.fullStrValue(2, RoundingMode.CEILING);
        System.out.println(s);
    }
}
