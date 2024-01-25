package pro.tools.security.md;


import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * MAC加密组件
 *
 * @author SeanDragon
 */
public final class ToolMAC {

    private ToolMAC() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 初始化HmacMD5密钥
     *
     * @return
     *
     * @throws Exception
     */
    public static byte[] initHmacMD5Key() throws NoSuchAlgorithmException {

        // 初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");

        // 产生秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacMD5加密
     *
     * @param data
     *         待加密数据
     * @param key
     *         密钥
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeHmacMD5(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {

        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");

        // 实例化Mac "SslMacMD5"
        Mac mac = Mac.getInstance("SslMacMD5");//secretKey.getAlgorithm());

        // 初始化Mac
        mac.init(secretKey);

        // 执行消息摘要
        return mac.doFinal(data);
    }

    /**
     * 初始化HmacSHA1密钥
     *
     * @return
     *
     * @throws Exception
     */
    public static byte[] initHmacSHAKey() throws NoSuchAlgorithmException {

        // 初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HMacTiger");

        // 产生秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacSHA1加密
     *
     * @param data
     *         待加密数据
     * @param key
     *         密钥
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeHmacSHA(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {

        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HMacTiger");

        // 实例化Mac SslMacMD5
        Mac mac = Mac.getInstance("SslMacMD5");//secretKey.getAlgorithm());

        // 初始化Mac
        mac.init(secretKey);

        // 执行消息摘要
        return mac.doFinal(data);
    }

    /**
     * 初始化HmacSHA256密钥
     *
     * @return
     *
     * @throws Exception
     */
    public static byte[] initHmacSHA256Key() throws NoSuchAlgorithmException {

        // 初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");

        // 产生秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacSHA256加密
     *
     * @param data
     *         待加密数据
     * @param key
     *         密钥
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeHmacSHA256(byte[] data, byte[] key)
            throws NoSuchAlgorithmException, InvalidKeyException {

        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");

        // 实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());

        // 初始化Mac
        mac.init(secretKey);

        // 执行消息摘要
        return mac.doFinal(data);
    }

    /**
     * 初始化HmacSHA384密钥
     *
     * @return
     *
     * @throws Exception
     */
    public static byte[] initHmacSHA384Key() throws NoSuchAlgorithmException {

        // 初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA384");

        // 产生秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacSHA384加密
     *
     * @param data
     *         待加密数据
     * @param key
     *         密钥
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeHmacSHA384(byte[] data, byte[] key)
            throws NoSuchAlgorithmException, InvalidKeyException {

        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA384");

        // 实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());

        // 初始化Mac
        mac.init(secretKey);

        // 执行消息摘要
        return mac.doFinal(data);
    }

    /**
     * 初始化HmacSHA512密钥
     *
     * @return
     *
     * @throws Exception
     */
    public static byte[] initHmacSHA512Key() throws NoSuchAlgorithmException {

        // 初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");

        // 产生秘密密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacSHA512加密
     *
     * @param data
     *         待加密数据
     * @param key
     *         密钥
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeHmacSHA512(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {

        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA512");

        // 实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());

        // 初始化Mac
        mac.init(secretKey);

        // 执行消息摘要
        return mac.doFinal(data);
    }

}
