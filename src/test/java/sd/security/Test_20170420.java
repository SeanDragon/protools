package sd.security;

import org.junit.Test;
import pro.tools.security.ToolBase64;

import java.security.NoSuchAlgorithmException;

/**
 * @author SeanDragon
 *         Create By 2017-04-20 14:39
 */
public class Test_20170420 {

    @Test
    public void test1() throws NoSuchAlgorithmException {
        String seanDragon = ToolBase64.encode("SeanDragon");
        System.out.println(ToolBase64.decode(seanDragon));
    }
}
