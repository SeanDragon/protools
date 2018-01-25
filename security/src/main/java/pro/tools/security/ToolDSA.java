package pro.tools.security;

import com.google.common.collect.Maps;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * DSA安全编码组件
 *
 * @author SeanDragon
 */
public final class ToolDSA {
    private ToolDSA() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 数字签名密钥算法
     */
    public static final String ALGORITHM = "DSA";

    /**
     * 数字签名 签名/验证算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withDSA";

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "DSAPublicKey";

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "DSAPrivateKey";

    /**
     * DSA密钥长度 默认1024位， 密钥长度必须是64的倍数， 范围在512至1024位之间（含）
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 签名
     *
     * @param data
     *         待签名数据
     * @param privateKey
     *         私钥
     *
     * @return byte[] 数字签名
     *
     * @throws Exception
     */
    public static byte[] sign(byte[] data, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // 还原私钥
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);

        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        // 生成私钥对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

        // 初始化Signature
        signature.initSign(priKey);

        // 更新
        signature.update(data);

        // 签名
        return signature.sign();
    }

    /**
     * 校验
     *
     * @param data
     *         待校验数据
     * @param publicKey
     *         公钥
     * @param sign
     *         数字签名
     *
     * @return boolean 校验成功返回true 失败返回false
     *
     * @throws Exception
     */
    public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // 还原公钥
        // 转换公钥材料
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);

        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        // 实例话Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

        // 初始化Signature
        signature.initVerify(pubKey);

        // 更新
        signature.update(data);

        // 验证
        return signature.verify(sign);
    }

    /**
     * 生成密钥
     *
     * @return 密钥对象
     *
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException {
        // 初始化密钥对儿生成器
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);

        // 实例化密钥对儿生成器
        keygen.initialize(KEY_SIZE, new SecureRandom());

        // 实例化密钥对儿
        KeyPair keys = keygen.genKeyPair();

        DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();

        DSAPrivateKey privateKey = (DSAPrivateKey) keys.getPrivate();

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
     *         密钥Map
     *
     * @return byte[] 公钥
     *
     * @throws Exception
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

}
