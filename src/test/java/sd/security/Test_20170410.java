package sd.security;

import org.junit.Test;
import pro.tools.data.ToolClone;
import pro.tools.http.jdk.ToolHttp;
import pro.tools.system.ToolClassSearch;
import pro.tools.time.DatePlus;

import java.time.LocalDateTime;

/**
 * 加密测试
 *
 * @author SeanDragon
 *         Create By 2017-04-10 10:00
 */
public class Test_20170410 {
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
