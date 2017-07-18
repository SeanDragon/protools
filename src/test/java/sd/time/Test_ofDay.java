package sd.time;

import org.junit.Test;
import pro.tools.time.DatePlus;

/**
 * @author SeanDragon
 *         <p>
 *         Create By 2017-07-17 9:33
 */
public class Test_ofDay {
    @Test
    public void test1() {
        DatePlus currentDatePlus = new DatePlus();
        DatePlus datePlus = new DatePlus(2016, 7, 31);

        long l = currentDatePlus.ofSeconds(datePlus);


        System.out.println(l);
    }

    @Test
    public void test2() {
        DatePlus currentDatePlus = new DatePlus();
        DatePlus datePlus = new DatePlus(2016, 6, 30);

        long l = currentDatePlus.ofMonth(datePlus);


        System.out.println(l);
    }
}
