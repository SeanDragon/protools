package sd.security;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import pro.tools.security.md.ToolMD5;

import java.security.NoSuchAlgorithmException;

/**
 * Created by tuhao on 2017/4/12.
 */
public class Test_20170412 {
    @Test
    public void testMD5_1() throws NoSuchAlgorithmException {
        byte[] bytes = ToolMD5.encodeMD5("123".getBytes());
        for (byte b : bytes) {
            System.out.print(b + "\t");
        }
        System.out.println(Hex.encodeHexString(bytes));
    }
}
