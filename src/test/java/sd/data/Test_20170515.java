package sd.data;

import org.junit.Test;
import pro.tools.time.DatePlus;
import pro.tools.time.DateType;

/**
 * @author SeanDragon
 *         Create By 2017-05-15 16:54
 */
public class Test_20170515 {
    @Test
    public void test1() {
        String dateStr = "2017-04-00";
        DatePlus datePlus = new DatePlus(dateStr, "yyyy-MM-dd").toMinDate(DateType.MONTH);
        System.out.println(datePlus);
        System.out.println(datePlus.toMaxDate(DateType.MONTH));
    }
}
