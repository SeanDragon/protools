package sd.time;

import org.junit.Test;
import pro.tools.time.DatePlus;

import java.time.temporal.ChronoUnit;

/**
 * @author SeanDragon
 *         <p>
 *         Create By 2017-07-17 9:33
 */
public class Test_ofDay {
    @Test
    public void test1() {
        DatePlus currentDatePlus = new DatePlus(2017, 7, 27, 19, 34);
        DatePlus datePlus = new DatePlus(2016, 7, 31, 0, 2, 5, 12);

        long l = currentDatePlus.ofDateTime(ChronoUnit.ERAS, datePlus);


        System.out.println(l);
    }

    @Test
    public void test2() {
        DatePlus currentDatePlus = new DatePlus();
        DatePlus datePlus = new DatePlus(1996, 6, 20);

        System.out.println(datePlus);

        //System.out.println(datePlus.addYear(-10).addSeconds(-26 * 60 * 60));

        System.out.println(datePlus.toLunar());
    }
}
