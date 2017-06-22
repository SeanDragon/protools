package sd.security;

import org.junit.Test;
import pro.tools.data.ToolClone;
import pro.tools.http.jdk.ToolHttp;
import pro.tools.http.jdk.ToolHttpReceive;
import pro.tools.http.jdk.ToolHttpSend;
import pro.tools.http.jdk.Tool_HTTP_METHOD;
import pro.tools.system.ToolClassSearch;
import pro.tools.system.ToolThreadPool;
import pro.tools.time.DatePlus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 加密测试
 *
 * @author SeanDragon
 *         Create By 2017-04-10 10:00
 */
public class Test_20170410 {
    public static void main(String[] args) {
        String url = "http://192.168.15.13/capital_operator/freeze?a=1&b=2&z=3&d=5&e=5&f=o&g=htr&h=辅导授课风格基金&key=4561322165";
        //url = "https://f.tuhaolicai.cc/userLoginPage";
        ToolHttpSend send = new ToolHttpSend(url, null);
        send.setMethod(Tool_HTTP_METHOD.GET);
        ToolThreadPool threadPool = new ToolThreadPool(ToolThreadPool.Type.CachedThread, 100);

        final int[] count = {0};
        final int[] errCount = {0};
        final List<Long> times = new ArrayList<>(10000);
        final long[] total = {0};

        for (int i = 0; i < 100; i++) {
            threadPool.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    long begin = System.currentTimeMillis();
                    ToolHttpReceive receive = ToolHttp.sendHttp(send);
                    long x = System.currentTimeMillis() - begin;
                    times.add(x);
                    total[0] += x;
                    ++count[0];

                    if (receive.isHaveError()) {
                        ++errCount[0];
                    }

                    if (count[0] == 900) {
                        System.out.println(receive.getResponseBody());
                        Object[] objects = times.toArray();
                        Arrays.sort(objects);
                        System.out.println(Arrays.toString(objects));
                        System.out.println("avg:\t" + (total[0] / objects.length));
                        System.out.println(errCount[0]);
                        System.exit(-1);
                    }
                }
            });
        }
    }

    @Test
    public void test1() {
        DatePlus datePlus1 = new DatePlus(2017, 3, 1);
        DatePlus datePlus2 = ToolClone.clone(datePlus1);
        datePlus1.addDay(-1);
        LocalDateTime localDateTime2 = datePlus2.getLocalDateTime();
        localDateTime2 = localDateTime2.minusDays(Long.MIN_VALUE);

        System.out.println(datePlus1.getLocalDateTime());
        System.out.println(localDateTime2);
    }

    @Test
    public void one() {
        long begin = System.currentTimeMillis();
        String url = "http://192.168.15.13/capital_operator/freeze?a=1&b=2&z=3&d=5&e=5&f=o&g=htr&h=辅导授课风格基金&key=4561322165";
        ToolHttpSend send = new ToolHttpSend(url, null);
        send.setMethod(Tool_HTTP_METHOD.POST);
        ToolHttp.sendHttp(send);
        ToolHttp.sendHttp(send);
        ToolHttp.sendHttp(send);
        ToolHttp.sendHttp(send);
        ToolHttp.sendHttp(send);
        ToolHttp.sendHttp(send);
        ToolHttp.sendHttp(send);
        ToolHttp.sendHttp(send);
        ToolHttp.sendHttp(send);
        System.out.println(System.currentTimeMillis() - begin);
    }

    @Test
    public void test3() {
        //String url = "http://192.168.15.20:8088/mt/sysconfig/menus";
        ////url = "http://192.168.15.20:8088/admin/menu/menus";
        //ToolHttpReceive receive = ToolHttp.sendGet(url);
        //System.out.println(receive.getResponseBody());

        String json = ToolHttp.sendGet("http://192.168.15.20:8088/admin/menu/menus").getResponseBody();
        System.out.println(json);

    }

    @Test
    public void test4() {
        System.out.println(ToolClassSearch.getAllClazz());
    }
}
