package pro.tools.security.md;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * SHA-2 的变种 SHA- 224、 SHA- 256、 SHA-384 、 SHA-512
 *
 * @author SeanDragon
 */
public final class ToolSHA2 {
    private ToolSHA2() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * SHA-224加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeSHA224(byte[] data) throws NoSuchAlgorithmException {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-224");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * SHA-224加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeSHA224Hex(byte[] data) throws NoSuchAlgorithmException {
        // 执行消息摘要
        byte[] b = encodeSHA224(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

    /**
     * SHA-256加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeSHA256(byte[] data) throws NoSuchAlgorithmException {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * SHA-256加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeSHA256Hex(byte[] data) throws NoSuchAlgorithmException {
        // 执行消息摘要
        byte[] b = encodeSHA256(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

    /**
     * SHA-384加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeSHA384(byte[] data) throws NoSuchAlgorithmException {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-384");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * SHA-384加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeSHA384Hex(byte[] data) throws NoSuchAlgorithmException {
        // 执行消息摘要
        byte[] b = encodeSHA384(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

    /**
     * SHA-512加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeSHA512(byte[] data) throws NoSuchAlgorithmException {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * SHA-512加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeSHA512Hex(byte[] data) throws NoSuchAlgorithmException {
        // 执行消息摘要
        byte[] b = encodeSHA512(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

}
