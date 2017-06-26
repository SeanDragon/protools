package sd.http.netty;

import com.google.common.base.MoreObjects;
import io.netty.buffer.ByteBuf;
import org.junit.Test;
import pro.tools.constant.HttpConst;
import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolJson;
import pro.tools.http.netty.clientpool.ClientPool;
import pro.tools.http.netty.clientpool.NettyClientPool;
import pro.tools.http.netty.http.Request;
import pro.tools.http.netty.http.Response;
import pro.tools.system.ToolThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SeanDragon Create By 2017-06-22 9:49
 */
public class Test_20170622 {

    private static final String address = "127.0.0.1";
    private static final int port = 88;
    private static final ClientPool pool = new NettyClientPool(10, address, port);
    private static int count = 0;

    /**
     * 异步调用
     *
     * @param path
     * @param method
     */
    public static void send(String path, HttpConst.RequestMethod method) {
        Request request = new Request(path, method);
        Map<String, Object> params = new HashMap<>();
        params.put("a", "商宇龙");
        params.put("b", "b=2&a=b=2");
        request.setParams(params);
        //pool.request(request).addListener(new Future.Listener() {
        //    @Override
        //    public void complete(Object arg) {
        //        Response response = (Response) arg;
        //        String result = response.getBody().toString(Charset.forName("utf-8"));
        //        //System.out.print("\t" + ++count + "\t" + result.length());
        //        //System.out.println("Test_20170622.complete");
        //        //System.out.println(result);
        //    }
        //
        //    @Override
        //    public void exception(Throwable t) {
        //        t.printStackTrace();
        //    }
        //});
        try {
            Response response = pool.requestWithTimeOut(request, 1200).sync();
            ByteBuf byteBuf = response.getBody();
            String body = byteBuf.toString(StrConst.DEFAULT_CHARSET);
            System.out.print("\t" + ++count + "\t" + body.length());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    //public static void doGetSync() throws Throwable {
    //    final ClientPool pool = new NettyClientPool(2, "http://", "192.168.15.13");
    //    pool.start();
    //    Request request = new Request("/public/index", HttpConst.RequestMethod.GET);
    //    request.getBody().writeBytes("aa".getBytes(Charset.forName("utf-8")));
    //    Response response = pool.requestWithTimeOut(request, 2000).sync();
    //    pool.stop();
    //    System.out.println(response.getBody().toString(Charset.forName("utf-8")));
    //}

    public static void main(String args[]) throws Throwable {
        long b = System.currentTimeMillis();
        pool.start();
        final ToolThreadPool threadPool = new ToolThreadPool(ToolThreadPool.Type.CachedThread, 100);
        final int[] count = {0};
        final List<Long> times = new ArrayList<>(100 * 20);
        final long[] total = {0};

        for (int i = 0; i < 100; i++) {
            threadPool.submit(() -> {
                for (int j = 0; j < 20; j++) {
                    long begin = System.currentTimeMillis();
                    send("/getService", HttpConst.RequestMethod.POST);
                    //send("/testProTools", HttpConst.RequestMethod.GET);
                    //send("/wp-includes/js/wp-embed.min.js?ver=4.7.5", HttpConst.RequestMethod.GET);
                    //send("/public/index/", HttpConst.RequestMethod.GET);
                    //send("/#ie=UTF-8&wd=SeanDragon", HttpConst.RequestMethod.GET);
                    long x = System.currentTimeMillis() - begin;
                    times.add(x);
                    total[0] += x;
                    ++count[0];
                    System.out.print(count[0] + "\t");
                    if (count[0] == 1990) {
                        Object[] objects = times.toArray();
                        Arrays.sort(objects);
                        System.out.println(Arrays.toString(objects));
                        System.out.println("avg:\t" + (total[0] / objects.length) / 1000D);
                        System.out.println((System.currentTimeMillis() - b) / 1000D);
                    }
                }
            });
        }
    }

    @Test
    public void test1() {
        pool.start();
        send("/testProTools", HttpConst.RequestMethod.POST);
    }

    @Test
    public void test2() {
        Menu menu = new Menu();
        menu.setMenuId(1D)
                .setName("商宇龙");

        Map map = ToolJson.modelToMap(menu);

        //JsonPrimitive menuId = (JsonPrimitive) map.get("name");
        //menuId = (JsonPrimitive) map.get("menuId");
        //
        //String s = menuId.toString();
        //try {
        //    StringWriter stringWriter = new StringWriter();
        //    JsonWriter jsonWriter = new JsonWriter(stringWriter);
        //    jsonWriter.setLenient(false);
        //    Streams.write(menuId, jsonWriter);
        //    s = stringWriter.toString();
        //    if (menuId.isString()) {
        //        s = s.substring(1, s.length() - 1);
        //    }
        //} catch (Exception var3) {
        //    throw new AssertionError(var3);
        //}

        //System.out.println(s);

        System.out.println(map.get("menuId"));
        System.out.println(map.get("name"));

        Menu newMenu = ToolJson.mapToModel(map, Menu.class);

        System.out.println(newMenu);
    }

    @Test
    public void test4() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu().setName("menu1").setMenuId(1D));
        menuList.add(new Menu().setName("menu2").setMenuId(2D));
        menuList.add(new Menu().setName("menu3").setMenuId(3D));

        String x = ToolJson.modelToJson(menuList);
        System.out.println(x);

        menuList = ToolJson.jsonToModelList(x, Menu.class);

        System.out.println(menuList);

        //String json = ToolJson.modelToMapList(menuList);
    }
}

class Menu {
    private String name;
    private Double menuId;

    public String getName() {
        return name;
    }

    public Menu setName(String name) {
        this.name = name;
        return this;
    }

    public Double getMenuId() {
        return menuId;
    }

    public Menu setMenuId(Double menuId) {
        this.menuId = menuId;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("menuId", menuId)
                .toString();
    }
}
