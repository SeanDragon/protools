package time;

import org.junit.Test;
import pro.tools.data.text.ToolJson;

import java.time.LocalDateTime;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-10-26 19:38
 */
public class TestLocalDateTime {
    @Test
    public void test1() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String x = ToolJson.anyToJson(localDateTime);
        System.out.println(x);
        LocalDateTime localDateTime1 = ToolJson.jsonToAny(x, LocalDateTime.class);
        System.out.println(localDateTime1);
    }

    @Test
    public void test2() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(ToolJson.anyToJson(localDateTime));
        String s = "2017-10-26 09:22:19:647";
        s = "{\"$timestamp\": \"2017-10-26 20:22:03:720\"}";
        LocalDateTime o = ToolJson.jsonToAny(s, LocalDateTime.class);
        System.out.println(o);
    }
}
