package sd.path;

import org.junit.Test;
import pro.tools.system.ToolClassSearch;
import pro.tools.system.ToolPath;

import java.util.Set;

/**
 * @author SeanDragon
 *         Create By 2017-06-07 13:24
 */
public class Test_20170607 {
    @Test
    public void test1() {
        Set<Class> allClazz = ToolClassSearch.getAllClazz();

        System.out.println(allClazz);

        System.out.println(ToolPath.getRootClassPath());
    }
}
