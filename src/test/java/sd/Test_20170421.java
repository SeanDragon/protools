package sd;

import org.junit.Test;
import pro.tools.system.ToolClassSearch;

import java.util.Set;

/**
 * @author SeanDragon
 *         Create By 2017-04-21 14:42
 */
public class Test_20170421 {
    @Test
    public void test1() {
        System.out.println(ToolClassSearch.getAllClazz());
        Set<Class<?>> clazzByAnnotation = ToolClassSearch.getClazzByAnnotation(Deprecated.class);
        System.out.println(clazzByAnnotation);
    }
}
