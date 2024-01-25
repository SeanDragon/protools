package pro.tools.security.md;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * RipeMD系列消息摘要组件<br>
 * 包含RipeMD128、RipeMD160、RipeMD256和RipeMD320共4种RipeMD系列算法
 *
 * @author SeanDragon
 */
public final class ToolRipeMD {
    private ToolRipeMD() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * RipeMD128消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeRipeMD128(byte[] data) throws NoSuchAlgorithmException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("RipeMD128");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * RipeMD128Hex消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeRipeMD128Hex(byte[] data) throws NoSuchAlgorithmException {

        // 执行消息摘要
        byte[] b = encodeRipeMD128(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

    /**
     * RipeMD160消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeRipeMD160(byte[] data) throws NoSuchAlgorithmException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("RipeMD160");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * RipeMD160Hex消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeRipeMD160Hex(byte[] data) throws NoSuchAlgorithmException {

        // 执行消息摘要
        byte[] b = encodeRipeMD160(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

    /**
     * RipeMD256消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeRipeMD256(byte[] data) throws NoSuchAlgorithmException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("RipeMD256");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * RipeMD256Hex消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeRipeMD256Hex(byte[] data) throws NoSuchAlgorithmException {

        // 执行消息摘要
        byte[] b = encodeRipeMD256(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

    /**
     * RipeMD320消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeRipeMD320(byte[] data) throws NoSuchAlgorithmException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("RipeMD320");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * RipeMD320Hex消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeRipeMD320Hex(byte[] data) throws NoSuchAlgorithmException {

        // 执行消息摘要
        byte[] b = encodeRipeMD320(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

}
