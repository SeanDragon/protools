package sd.data;

import org.junit.Test;
import pro.tools.data.text.ToolRegex;

/**
 * @author SeanDragon
 *         Create By 2017-04-10 10:54
 */
public class TestRegex {
    @Test
    public void test1() {
        String str = "商宇龙";
        System.out.println(ToolRegex.isZh(str));
    }

    @Test
    public void test2() {
        String d = "2.0";
        System.out.println(ToolRegex.isDecimal(d));

        String integer = "2";
        System.out.println(ToolRegex.isDecimal(integer));
        System.out.println(ToolRegex.isDecimal(integer));
        System.out.println(ToolRegex.isInteger(integer));
    }

    @Test
    public void test3() {
        String name = "_SeanDragon1";

        System.out.println(ToolRegex.isUsername(name));

        name = "_商宇龙";

        System.out.println(ToolRegex.isUsername(name));

    }
}
