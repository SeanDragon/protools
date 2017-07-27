package sd.decimal;

import org.junit.Test;
import pro.tools.data.decimal.Decimal;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-27 16:38
 */
public class TestBUG {
    @Test
    public void test1() {
        String d = "99999999999999999999999999999999.5555555555555555555555555555555555555";
        MathContext mathContext = new MathContext(Integer.MAX_VALUE, RoundingMode.HALF_EVEN);
        mathContext = MathContext.UNLIMITED;

        BigDecimal bigDecimal = new BigDecimal(d
                //, mathContext
        );

        Decimal instance = Decimal.instance(d
                //, mathContext
        );

        System.out.println(instance.moneyStrValue());
        System.out.println(instance.fullStrValue());
        System.out.println(instance.fullStrValue(10));
        System.out.println(bigDecimal.toPlainString());
    }
}
