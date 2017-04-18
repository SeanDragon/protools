package sd;

import pro.tools.system.ToolClassSearch;

import java.util.Set;

/**
 * Created by tuhao on 2017/4/12.
 */
public class Test_20170412 {

    public static void main(String[] args) {
        Object a = new ABC();
        Set<Class<?>> clazz = ToolClassSearch.getClazz(a.getClass());
        System.out.println(clazz);
    }

    static class ABC {

    }
}
