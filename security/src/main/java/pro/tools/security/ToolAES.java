package pro.tools.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * AES安全编码组件
 * <p>
 * 高级数据加密标准---AES：由于DES的问题所以产生了AES,像是DES的升级，密钥建立时间短，灵敏性好，内存要求低，被广泛应用
 * <p>
 * 说明：
 * <p>
 * 对于java.security.InvalidKeyException: Illegal key size or default
 * parameters异常， 去掉这种限制需要下载Java Cryptography Extension (JCE) Unlimited Strength
 * Jurisdiction Policy Files， 下载包的readme.txt
 * 有安装说明。就是替换${JAVA_HOME}/jre/lib/security/
 * 下面的local_policy.jar和US_export_policy.jar
 *
 * @author SeanDragon
 */
public final class ToolAES {
    private ToolAES() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";
    /**
     * 加密/解密算法 / 工作模式 / 填充方式 Java 6支持PKCS5Padding填充方式 Bouncy
     * Castle支持PKCS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ToolAES.class);

    /**
     * 转换密钥
     *
     * @param key
     *         二进制密钥
     *
     * @return Key 密钥
     *
     * @throws Exception
     */
    private static Key toKey(byte[] key) {
        // 实例化AES密钥材料
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);

        return secretKey;
    }

    /**
     * 解密
     *
     * @param data
     *         待解密数据
     * @param key
     *         密钥
     *
     * @return byte[] 解密数据
     *
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 还原密钥
        Key k = toKey(key);

        /*
         * 实例化 使用PKCS7Padding填充方式，按如下方式实现 Cipher.getInstance(CIPHER_ALGORITHM, "BC");
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);

        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data
     *         待加密数据
     * @param key
     *         密钥
     *
     * @return byte[] 加密数据
     *
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 还原密钥
        Key k = toKey(key);

        /*
         * 实例化 使用PKCS7Padding填充方式，按如下方式实现 Cipher.getInstance(CIPHER_ALGORITHM,
         * "BC");
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);

        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 生成密钥 <br>
     *
     * @return byte[] 二进制密钥
     *
     * @throws Exception
     */
    public static byte[] initKey() throws NoSuchAlgorithmException {
        // 实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

        /*
         * AES 要求密钥长度为 128位、192位或 256位
         */
        kg.init(256);

        // 生成秘密密钥
        SecretKey secretKey = kg.generateKey();

        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }


}
