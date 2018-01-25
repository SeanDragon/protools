package pro.tools.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
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
import java.security.Security;

/**
 * IDEA安全编码组件
 * <p>
 * 国际数据加密标准---IDEA：完全是新突破，几乎同时和AES出现
 *
 * @author SeanDragon
 */
public final class ToolIDEA {
    private ToolIDEA() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "IDEA";
    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     */
    public static final String CIPHER_ALGORITHM = "IDEA/ECB/PKCS5Padding";
    private static final Logger log = LoggerFactory.getLogger(ToolIDEA.class);

    static {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());
    }

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
        // 生成秘密密钥
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
    public static byte[] decrypt(byte[] data, byte[] key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        // 还原密钥
        Key k = toKey(key);

        // 实例化
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

        // 实例化
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

        // 初始化
        kg.init(128);

        // 生成秘密密钥
        SecretKey secretKey = kg.generateKey();

        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

}
