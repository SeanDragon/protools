package sd.time;

import org.junit.Test;
import pro.tools.time.DatePlus;
import pro.tools.time.DateType;

/**
 * @author SeanDragon
 *         Create By 2017-05-04 9:25
 */
public class Test_Month {
    @Test
    public void test1() {
        DatePlus datePlus = new DatePlus();
        int lastDayOfMonth = datePlus.getLastDayOfMonth();
        System.out.println(lastDayOfMonth);
    }

    @Test
    public void test2() {
        DatePlus datePlus = new DatePlus();
        System.out.println(datePlus);
        //System.out.println(datePlus.toMaxDate(DateType.SECONDS));
        //System.out.println(datePlus.toMinDate(DateType.SECONDS));
        //System.out.println(datePlus.toMaxDate(DateType.MINUTES));
        //System.out.println(datePlus.toMinDate(DateType.MINUTES));
        //System.out.println(datePlus.toMaxDate(DateType.HOUR));
        //System.out.println(datePlus.toMinDate(DateType.HOUR));
        //System.out.println(datePlus.toMaxDate(DateType.DAY));
        //System.out.println(datePlus.toMinDate(DateType.DAY));
        System.out.println(datePlus.toMaxDate(DateType.MONTH));
        System.out.println(datePlus.toMinDate(DateType.MONTH));
        System.out.println(datePlus.toMaxDate(DateType.YEAR));
        System.out.println(datePlus.toMinDate(DateType.YEAR));
    }
}
