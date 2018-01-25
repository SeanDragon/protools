package pro.tools.security.md;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * MD5加密组件
 *
 * @author SeanDragon
 */
public final class ToolMD5 {
    private ToolMD5() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * MD5加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encodeMD5(byte[] data) throws NoSuchAlgorithmException {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD5");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * MD5加密
     *
     * @param data
     *         待加密数据
     *
     * @return String 消息摘要
     *
     * @throws NoSuchAlgorithmException
     */
    public static String encodeMD5Hex(byte[] data) throws NoSuchAlgorithmException {
        // 执行消息摘要
        byte[] b = encodeMD5(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

}
