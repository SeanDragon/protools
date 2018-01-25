package pro.tools.security.md;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * MAC消息摘要组件
 *
 * @author SeanDragon
 */
public final class ToolMAC_BCP {

    private ToolMAC_BCP() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 初始化HmacMD2密钥
     *
     * @return byte[] 密钥
     *
     * @throws Exception
     */
    public static byte[] initHmacMD2Key() throws NoSuchAlgorithmException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD2");

        // 产生秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacMD2消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     * @param key
     *         密钥
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeHmacMD2(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacMD2");

        // 实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());

        // 初始化Mac
        mac.init(secretKey);

        // 执行消息摘要
        return mac.doFinal(data);
    }

    /**
     * HmacMD2Hex消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     * @param key
     *         密钥
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeHmacMD2Hex(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {

        // 执行消息摘要
        byte[] b = encodeHmacMD2(data, key);

        // 做十六进制转换
        return new String(Hex.encode(b));
    }

    /**
     * 初始化HmacMD4密钥
     *
     * @return byte[] 密钥
     *
     * @throws Exception
     */
    public static byte[] initHmacMD4Key() throws NoSuchAlgorithmException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD4");

        // 产生秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacMD4消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     * @param key
     *         密钥
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeHmacMD4(byte[] data, byte[] key)
            throws NoSuchAlgorithmException, InvalidKeyException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacMD4");

        // 实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());

        // 初始化Mac
        mac.init(secretKey);

        // 执行消息摘要
        return mac.doFinal(data);
    }

    /**
     * HmacMD4Hex消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     * @param key
     *         密钥
     *
     * @return String 消息摘要
     *
     * @throws Exception
     */
    public static String encodeHmacMD4Hex(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {

        // 执行消息摘要
        byte[] b = encodeHmacMD4(data, key);

        // 做十六进制转换
        return new String(Hex.encode(b));
    }

    /**
     * 初始化HmacSHA224密钥
     *
     * @return byte[] 密钥
     *
     * @throws Exception
     */
    public static byte[] initHmacSHA224Key() throws NoSuchAlgorithmException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA224");

        // 产生秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacSHA224消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     * @param key
     *         密钥
     *
     * @return byte[] 消息摘要
     *
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] encodeHmacSHA224(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {

        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA224");

        // 实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());

        // 初始化Mac
        mac.init(secretKey);

        // 执行消息摘要
        return mac.doFinal(data);
    }

    /**
     * HmacSHA224Hex消息摘要
     *
     * @param data
     *         待做消息摘要处理的数据
     * @param key
     *         密钥
     *
     * @return String 消息摘要
     *
     * @throws Exception
     */
    public static String encodeHmacSHA224Hex(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {

        // 执行消息摘要
        byte[] b = encodeHmacSHA224(data, key);

        // 做十六进制转换
        return new String(Hex.encode(b));
    }

}
