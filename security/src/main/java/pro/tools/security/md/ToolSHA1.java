package pro.tools.security.md;

import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-1加密
 *
 * @author SeanDragon
 */
public final class ToolSHA1 {
    private ToolSHA1() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * SHA-1加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeSHA(byte[] data) throws NoSuchAlgorithmException {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * SHA-1加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeSHAHex(byte[] data) throws NoSuchAlgorithmException {
        // 执行消息摘要
        byte[] b = encodeSHA(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

}
