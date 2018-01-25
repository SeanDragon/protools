package pro.tools.security;

import com.google.common.collect.Maps;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
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
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * ECDSA安全编码组件
 *
 * @author SeanDragon
 */
public final class ToolECDSA {
    private ToolECDSA() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 数字签名 密钥算法
     */
    private static final String KEY_ALGORITHM = "ECDSA";

    /**
     * 数字签名 签名/验证算法
     * <p>
     * Bouncy Castle支持以下7种算法
     * NONEwithECDSA
     * RIPEMD160withECDSA
     * SHA1withECDSA
     * SHA224withECDSA
     * SHA256withECDSA
     * SHA384withECDSA
     * SHA512withECDSA
     */
    private static final String SIGNATURE_ALGORITHM = "SHA512withECDSA";

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "ECDSAPublicKey";

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "ECDSAPrivateKey";

    /**
     * 初始化密钥
     *
     * @return Map 密钥Map
     *
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        BigInteger p = new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839");

        ECFieldFp ecFieldFp = new ECFieldFp(p);

        BigInteger a = new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16);

        BigInteger b = new BigInteger("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a", 16);

        EllipticCurve ellipticCurve = new EllipticCurve(ecFieldFp, a, b);

        BigInteger x = new BigInteger("110282003749548856476348533541186204577905061504881242240149511594420911");

        BigInteger y = new BigInteger("869078407435509378747351873793058868500210384946040694651368759217025454");

        ECPoint g = new ECPoint(x, y);

        BigInteger n = new BigInteger("883423532389192164791648750360308884807550341691627752275345424702807307");

        ECParameterSpec ecParameterSpec = new ECParameterSpec(ellipticCurve, g, n, 1);

        // 实例化密钥对儿生成器
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        // 初始化密钥对儿生成器
        kpg.initialize(ecParameterSpec, new SecureRandom());

        // 生成密钥对儿
        KeyPair keypair = kpg.generateKeyPair();

        ECPublicKey publicKey = (ECPublicKey) keypair.getPublic();

        ECPrivateKey privateKey = (ECPrivateKey) keypair.getPrivate();

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
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);

        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
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
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());

        // 转换公钥材料
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);

        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 生成公钥
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

        // 初始化Signature
        signature.initVerify(pubKey);

        // 更新
        signature.update(data);

        // 验证
        return signature.verify(sign);
    }

}
