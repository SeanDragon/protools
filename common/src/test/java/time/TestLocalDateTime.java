package time;

import org.junit.Test;
import pro.tools.data.text.ToolJson;
import pro.tools.time.DatePlus;
import pro.tools.time.DateType;

import java.time.LocalDate;
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

    @Test
    public void test3() {
        LocalDate localDate = LocalDate.now();
        String x = ToolJson.anyToJson(localDate);
        System.out.println(x);
        LocalDate localDate1 = ToolJson.jsonToAny(x, LocalDate.class);
        System.out.println(localDate1);
    }

    @Test
    public void test4() {
        DatePlus datePlus = new DatePlus();
        System.out.println(datePlus);
        String s = ToolJson.anyToJson(datePlus);
        System.out.println(s);
        DatePlus t = ToolJson.jsonToAny(s, DatePlus.class);
        System.out.println(t);
    }

    @Test
    public void test5() {
        DatePlus datePlus1 = new DatePlus();
        DatePlus datePlus2 = new DatePlus();

        System.out.println(datePlus1);
        System.out.println(datePlus2);
        System.out.println();

        boolean before = datePlus1.isBefore(datePlus2, DateType.SECONDS);
        System.out.println(before);
        boolean same = datePlus1.isSame(datePlus2, DateType.SECONDS);
        System.out.println(same);
        boolean after = datePlus1.isAfter(datePlus2, DateType.SECONDS);
        System.out.println(after);
    }
}
