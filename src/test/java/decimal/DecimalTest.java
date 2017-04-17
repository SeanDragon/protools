//package decimal;
//
//import org.junit.Test;
//import pro.tools.data.decimal.Decimal;
//import pro.tools.decimal;
//
//import java.math.BigDecimal;
//import java.math.MathContext;
//import java.math.RoundingMode;
//
///**
// *
// * Created by tuhao on 2017/4/13.
// */
//
//public class DecimalTest {
//
//    @Test
//    public void decimalTest() {
//        BigDecimal bigDecimal = decimal.get(1.52D);
//        bigDecimal = bigDecimal.negate();
//        System.out.println(bigDecimal.doubleValue());
//        bigDecimal = bigDecimal.negate(MathContext.DECIMAL32);
//        System.out.println(bigDecimal.doubleValue());
//    }
//
//    @Test
//    public void test1() {
//        Decimal decimal = new Decimal("11");
//        decimal.add(1).sub(1).mul(1).div(1);
//        System.out.println(decimal.fullStrValue());
//        System.out.println(decimal.getDivGetInteger(2));
//        System.out.println(decimal.fullStrValue());
//        System.out.println(decimal.getRemainder(2));
//        System.out.println(decimal.fullStrValue());
//    }
//
//    @Test
//    public void tes2() {
//        Decimal add = new Decimal("159753");
//        //System.out.println(add.moneyValue());
//        long l = System.currentTimeMillis();
//        System.out.println(add.sqrt2(99999999).fullStrValue());
//        System.err.println("1----------" + (System.currentTimeMillis() - l));
//        l = System.currentTimeMillis();
//        add = new Decimal("159753");
//        double a = add.doubleValue();
//        System.out.println(Math.sqrt(a));
//        System.err.println("2----------" + (System.currentTimeMillis() - l));
//    }
//
//    @Test
//    public void test3() {
//        Decimal decimal = new Decimal(555544.23252);
//        System.out.println(decimal.doubleValue());
//        System.out.println(decimal.doubleValue(2, RoundingMode.HALF_EVEN));
//        System.out.println(decimal.doubleValue(3, RoundingMode.HALF_EVEN));
//    }
//}
