package sd.time;

import org.junit.Test;
import pro.tools.time.DatePlus;
import pro.tools.time.ToolLunar;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * Created on 17/4/8 18:48 星期六.
 *
 * @author SeanDragon
 */
public class Test_LocalDate {
    //
    //private LocalDate today = ToolLocalDate.getNow_LocalDate();
    //
    //@Test
    //public void test1() {
    //    LocalDate localDate = ToolLocalDate.getLocalDate(2017, 4, 8);
    //
    //    boolean equals = today.equals(localDate);
    //
    //    System.out.println(equals);
    //}
    //
    //@Test
    //public void test2() {
    //    LocalDate dateOfBirth = LocalDate.of(2010, 4, 8);
    //    MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
    //    MonthDay currentMonthDay = MonthDay.from(today);
    //    if (currentMonthDay.equals(birthday)) {
    //        System.out.println("Many Many happy returns of the day !!");
    //    } else {
    //        System.out.println("Sorry, today is not your birthday");
    //    }
    //}

    @Test
    public void test3() {
        Clock clock = Clock.systemDefaultZone();
        Clock clock1 = Clock.systemUTC();

        System.out.println(clock);
        System.out.println(clock1);
        System.out.println(clock.millis());
        System.out.println(clock1.millis());
        System.out.println(clock.instant());
        System.out.println(clock1.instant());
        System.out.println(clock.equals(clock1));

        LocalDateTime parse = LocalDateTime.parse("20101030112201", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        //DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).parse("");
        System.out.println(parse);
    }

    @Test
    public void test4() {
        //long l = System.currentTimeMillis();
        ////DatePlus datePlus = new DatePlus();
        //System.out.println(new DatePlus());
        ////System.out.println(datePlus.toDate());
        //System.out.println(System.currentTimeMillis() - l);
        //
        //l = System.currentTimeMillis();
        ////System.out.println(new Date(new Date(System.currentTimeMillis()).getTime()));
        //System.out.println(new Date());
        //System.out.println(System.currentTimeMillis() - l);
        //
        //l = System.currentTimeMillis();
        //System.out.println(new DatePro().getDate());
        //System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void test5() throws InterruptedException {
        DatePlus x = new DatePlus();
        Thread.sleep(1);
        DatePlus y = new DatePlus();
        System.out.println(x);
        System.out.println(y);
        System.out.println(x.equals(y));
    }

    @Test
    public void test6() {
        DatePlus d = new DatePlus();
        System.out.println(d);
        //DatePlus datePlus = d.addDay(23);
        d.addHour(23 * 24);
        DatePlus datePlus = new DatePlus();
        System.out.println(d);
        System.out.println(datePlus);
        long x = datePlus.ofSeconds(d);
        System.out.println(x);
        System.out.println(x / 60.0 / 60 / 24);

        System.out.println(String.format("今天%tR", System.currentTimeMillis()));
    }

    @Test
    public void test7() throws InterruptedException {
        long l = System.currentTimeMillis();
        DatePlus datePlus = new DatePlus();

        //Thread.sleep(1);

        DatePlus datePlus1 = new DatePlus();
        System.out.println(datePlus);
        System.out.println(datePlus1);
        System.out.println(datePlus1.toLunar());
        System.out.println(datePlus1.toSolar());
        System.out.println(datePlus1.isBefore(datePlus));
        System.out.println(datePlus1.isSame(datePlus));
        System.out.println(datePlus1.isAfter(datePlus));
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void test8() {
        ToolLunar.Lunar lunar = new ToolLunar.Lunar(2017, 3, 12);
        ToolLunar.Solar solar = ToolLunar.LunarToSolar(lunar);
        System.out.println(solar);
    }

    @Test
    public void test9() {
        DatePlus datePlus = new DatePlus();
        String s = datePlus.toGanZhi();
        String s1 = datePlus.toChineseZodiac();
        String s2 = datePlus.toZodiac();
        System.out.println(s);
        System.out.println(s1);
        System.out.println(s2);

        System.out.println(datePlus.toString());
        System.out.println(datePlus.toString("yyyy年MM月dd日"));
    }

    @Test
    public void test10() {
        DatePlus datePlus = new DatePlus(2017, 5, 5);
        LocalDateTime localDateTime = (datePlus.getLocalDateTime()).with(TemporalAdjusters.firstDayOfYear());
        System.out.println(localDateTime);
    }
}
