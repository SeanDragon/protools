package sd.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import pro.tools.data.ToolSerialize;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-26 16:53
 */
public class TestSerialize {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(1);

    @Test
    public void test1() {

        AA aa = new AA();
        aa.map = Maps.newHashMap();
        aa.list = Lists.newArrayList();

        for (int i = 0; i < 10000; i++) {
            aa.map.put("" + ATOMIC_INTEGER.getAndIncrement(), "i");
        }

        long begin1 = System.currentTimeMillis();
        byte[] serialize = ToolSerialize.serialize(aa);
        long end1 = System.currentTimeMillis();

        long begin2 = System.currentTimeMillis();
        AA unserialize = ToolSerialize.unserialize(serialize, AA.class);
        long end2 = System.currentTimeMillis();

        System.out.println(aa.hashCode());
        //System.out.println(Arrays.toString(serialize));
        System.out.println(unserialize.hashCode());

        System.out.println(end1 - begin1);
        System.out.println(end2 - begin2);

        System.out.println(end2 - begin1);
    }
}


class AA {
    Map map;
    List list;
}