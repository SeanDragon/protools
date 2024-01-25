package pro.tools.security;

import com.google.common.collect.Maps;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.DHParameterSpec;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * ElGamal安全编码组件
 *
 * @author SeanDragon
 */
public final class ToolElGamal {
    private ToolElGamal() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 非对称加密密钥算法
     */
    public static final String KEY_ALGORITHM = "ElGamal";

    /**
     * 密钥长度
     * <p>
     * ElGamal算法默认密钥长度为1024 密钥长度范围在160位至16,384位不等。
     */
    private static final int KEY_SIZE = 256;

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "ElGamalPublicKey";

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "ElGamalPrivateKey";

    /**
     * 用私钥解密
     *
     * @param data
     *         待解密数据
     * @param key
     *         私钥
     *
     * @return byte[] 解密数据
     *
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 私钥材料转换
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);

        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 生成私钥
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     *
     * @param data
     *         待加密数据
     * @param key
     *         公钥
     *
     * @return byte[] 加密数据
     *
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 公钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);

        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 生成公钥
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 生成密钥
     *
     * @return Map 密钥Map
     *
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 实例化算法参数生成器
        AlgorithmParameterGenerator apg = AlgorithmParameterGenerator.getInstance(KEY_ALGORITHM);

        // 初始化算法参数生成器
        apg.init(KEY_SIZE);

        // 生成算法参数
        AlgorithmParameters params = apg.generateParameters();

        // 构建参数材料
        DHParameterSpec elParams = params.getParameterSpec(DHParameterSpec.class);

        // 实例化密钥对儿生成器
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        // 初始化密钥对儿生成器
        kpg.initialize(elParams, new SecureRandom());

        // 生成密钥对儿
        KeyPair keys = kpg.genKeyPair();

        // 取得密钥
        PublicKey publicKey = keys.getPublic();

        PrivateKey privateKey = keys.getPrivate();

        // 封装密钥
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);

        map.put(PUBLIC_KEY, publicKey);

        map.put(PRIVATE_KEY, privateKey);

        return map;
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     *         密钥Map
     *
     * @return byte[] 私钥
     *
     * @throws Exception
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     *
     * @return
     *
     * @throws Exception
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

}
