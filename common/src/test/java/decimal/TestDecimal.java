package decimal;

import org.junit.Test;
import pro.tools.data.decimal.Decimal;
import pro.tools.data.text.ToolJson;

import java.math.RoundingMode;
import java.time.LocalDateTime;

public class TestDecimal {
    @Test
    public void test1() {
        Decimal instance = Decimal.instance(1).div(3);
        String s = instance.fullStrValue();
        instance.fullStrValue(2, RoundingMode.CEILING);
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
