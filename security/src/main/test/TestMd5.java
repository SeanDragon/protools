import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolJson;
import pro.tools.security.md.ToolMD5;

import java.security.NoSuchAlgorithmException;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-11-30 16:58
 */
public class TestMd5 {
    @Test
    public void test1() throws NoSuchAlgorithmException {
        byte [] data = "123456".getBytes();
        long begin = System.currentTimeMillis();
        String result = ToolMD5.encodeMD5Hex(data);
        System.out.println(System.currentTimeMillis()-begin);
        System.out.println(result);
        begin = System.currentTimeMillis();
        HashFunction hashFunction = Hashing.md5();
        Hasher hasher = hashFunction.newHasher();
        hasher.putString("123456", StrConst.DEFAULT_CHARSET);
        HashCode hash = hasher.hash();
        System.out.println(System.currentTimeMillis()-begin);
        System.out.println(hash.toString());
        System.out.println(Hex.toHexString(hash.asBytes()));
    }

    @Test
    public void test2() {
        System.out.println(ToolJson.anyToJson(null));
    }
}
