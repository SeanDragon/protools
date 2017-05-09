package sd.data;

import org.junit.Test;
import pro.tools.data.text.ToolRandoms;

import java.util.Arrays;

/**
 * @author SeanDragon
 *         Create By 2017-04-25 10:17
 */
public class TestRandom implements java.io.Serializable {
    public void doOne() {
        System.out.println(ToolRandoms.getAuthCodeAll(4));
        System.out.println(ToolRandoms.getAuthCodeAllChar());
        System.out.println(ToolRandoms.getAuthCodeNumber(4));
        System.out.println(Arrays.toString(ToolRandoms.getRandomRgb()));
        System.out.println(ToolRandoms.getNumberRandom(4));
        System.out.println(ToolRandoms.getNumberRandom(1, 4));
        System.out.println(ToolRandoms.getUuid(true));
        System.out.println(ToolRandoms.getUuid(false));
        System.out.println(ToolRandoms.getRandomStrByNanoTime(true));
        System.out.println(ToolRandoms.getRandomStrByNanoTime(false));
    }

    @Test
    public void test2() {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            test3();
        }
        System.out.println(System.currentTimeMillis() - begin);
    }

    @Test
    public void test3() {
        System.out.println(ToolRandoms.getRandomStr());
    }
}
