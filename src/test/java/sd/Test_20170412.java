package sd;

import pro.tools.system.ToolClassSearch;

import java.util.Set;

/**
 * Created by tuhao on 2017/4/12.
 */
public class Test_20170412 {

    public static void main(String[] args) {
        Set<Class<?>> allClazz = ToolClassSearch.getAllClazz();
        allClazz.forEach(System.out::println);
    }
}
