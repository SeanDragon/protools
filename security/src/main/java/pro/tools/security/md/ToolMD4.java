package pro.tools.security.md;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * MD4加密组件
 *
 * @author SeanDragon
 */
public final class ToolMD4 {
    private ToolMD4() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * MD4加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeMD4(byte[] data) throws NoSuchAlgorithmException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD4");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * MD4加密
     *
     * @param data
     *         待加密数据
     *
     * @return String 消息摘要
     *
     * @throws Exception
     */
    public static String encodeMD4Hex(byte[] data) throws NoSuchAlgorithmException {

        // 执行消息摘要
        byte[] b = encodeMD4(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

}
