package decimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pro.tools.data.decimal.Decimal;
import pro.tools.data.text.ToolJson;

import java.math.RoundingMode;
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
}
