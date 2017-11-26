package decimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pro.tools.data.decimal.Decimal;
import pro.tools.data.text.ToolJson;
import pro.tools.data.text.ToolRandoms;

import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@RunWith(JUnit4.class)
public class TestDecimal {
    @Test(timeout = 1000)
    public void test1() {
        Decimal instance = Decimal.instance(1).div(3);
        String s = instance.fullStrValue();
        instance.fullStrValue(2, RoundingMode.HALF_EVEN);
        System.out.println(s);
    }

    @Test
    public void test2() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String localDateTimeStr = ToolJson.anyToJson(localDateTime);
        System.out.println(localDateTimeStr);
        LocalDateTime localDateTime1 = ToolJson.jsonToModel(localDateTimeStr, LocalDateTime.class);
        System.out.println(localDateTime1);
    }

    @Test
    public void test3() {
        String s = "{value: \"2017-11-24 18:55:24:373\"}";
        System.out.println(ToolJson.jsonToModel(s, LocalDateTime.class));
    }

    @Test(timeout = 2 * 60 * 1000)
    public void test4() {

        String s = "";

        for (int i = 0; i < 100000; i++) {
            s = s.concat(ToolRandoms.getAuthCodeNumber(1));
        }
        s = s.concat(".");
        for (int i = 0; i < 100000; i++) {
            s = s.concat(ToolRandoms.getAuthCodeNumber(1));
        }

        Decimal decimal1 = Decimal.instance(s, MathContext.UNLIMITED);
        decimal1.sqrt2(100000);

        System.out.println(decimal1.fullStrValue());
    }

    @Test
    public void test5() {
        String authCodeAll = ToolRandoms.getAuthCodeAll(2);
        System.out.println(authCodeAll);

        System.out.println(ToolRandoms.getAuthCodeAllChar());
    }

    @Test
    public void test6() {
        Decimal instance = Decimal.instance(3,MathContext.UNLIMITED);
        Decimal sqrt2 = instance.sqrt2(10000);
        System.out.println(sqrt2);
        System.out.println(sqrt2.mul(sqrt2));
    }
}
