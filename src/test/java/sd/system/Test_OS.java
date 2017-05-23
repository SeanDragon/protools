package sd.system;

import org.junit.Test;
import pro.tools.system.ToolOS;
import pro.tools.system.ToolSystem;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @author SeanDragon
 *         Create By 2017-04-10 17:31
 */
public class Test_OS {
    @Test
    public void test1() throws UnknownHostException {
        System.out.println(ToolOS.getInetAddress());
        System.out.println(ToolOS.getJvmThreads());
    }

    @Test
    public void test2() throws IOException {
        boolean b = ToolSystem.haveDiskD();
        System.out.println(b);
    }
}
