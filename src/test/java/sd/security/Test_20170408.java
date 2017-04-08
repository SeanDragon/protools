package sd.security;

import org.junit.Test;
import pro.tools.security.md.ToolHmacRipeMD;
import pro.tools.security.md.ToolMAC;
import pro.tools.security.md.ToolMD5;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created on 17/4/8 09:00 星期六.
 *
 * @author sd
 */
public class Test_20170408 {

    String text = "123";
    String key = "123";

    @Test
    public void test_HmacRipeMD() throws NoSuchAlgorithmException, InvalidKeyException {
        String result = ToolHmacRipeMD.encodeHmacRipeMD160Hex(text.getBytes(), key.getBytes());
        System.out.println(result);
        /*
        5add6f4d1d38ba5abae3f59b099b2818c635c0cf
         */
    }

    @Test
    public void test_Mac() throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] bytes = ToolMAC.encodeHmacMD5(text.getBytes(), key.getBytes());
        System.out.println(Arrays.toString(bytes));
        /*
        [13, -124, 108, -25, -114, 126, 30, -73, -117, 111, 58, 127, 102, -89, -84, -56]
         */
    }

    @Test
    public void test_MacBCP() throws NoSuchAlgorithmException {
        String s = ToolMD5.encodeMD5Hex(text.getBytes());
        System.out.println(s);
        /*
        202cb962ac59075b964b07152d234b70
         */
    }
}
