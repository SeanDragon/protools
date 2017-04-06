package pro.tools;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 签名、验证、加密、解密帮助类
 *
 * @author sam
 */
public class sign {
    private final static Logger log = Logger.getLogger(sign.class);
    private static final sign rsaHelper = new sign();
    // 签名对象
    private Signature signature;
    private String pubkey;
    private String prikey;

    private sign() {
        try {
            signature = Signature.getInstance("SHA1withRSA");
        } catch (NoSuchAlgorithmException nsa) {
            log.error("" + nsa.getMessage());
        }
    }

    public static sign instance() {
        return rsaHelper;
    }

    /**
     * 得到私钥字符串（经过base64编码）
     *
     * @return
     */
    public static String getPriKeyString(PrivateKey key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }

    /**
     * 得到公钥字符串（经过base64编码）
     *
     * @return
     */
    public static String getPubKeyString(PublicKey key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }

    private PrivateKey getPrivateKey(String privateKeyStr) {
        try {
            byte[] privateKeyBytes = b64decode(privateKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException e) {
            log.error("Invalid Key Specs. Not valid Key files." + e.getCause());
            return null;
        } catch (NoSuchAlgorithmException e) {
            log.error("There is no such algorithm. Please check the JDK ver." + e.getCause());
            return null;
        }
    }

    private PublicKey getPublicKey(String publicKeyStr) {
        try {
            byte[] publicKeyBytes = b64decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            log.error("Invalid Key Specs. Not valid Key files." + e.getCause());
            return null;
        } catch (NoSuchAlgorithmException e) {
            log.error("There is no such algorithm. Please check the JDK ver." + e.getCause());
            return null;
        }
    }

    /**
     * RSA 数据签名
     *
     * @param toBeSigned (待签名的原文)
     * @param priKey     (RSA私钥）
     * @return （返回RSA签名后的数据签名数据base64编码）
     */
    public String signData(String toBeSigned, String priKey) {

        try {
            PrivateKey privateKey = getPrivateKey(priKey);
            byte[] signByte = toBeSigned.getBytes("utf-8");
            Signature rsa = Signature.getInstance("SHA1withRSA");
            rsa.initSign(privateKey);
            rsa.update(signByte);
            return b64encode(rsa.sign());
        } catch (NoSuchAlgorithmException ex) {
            log.error(ex);
        } catch (InvalidKeyException in) {
            log.error("Invalid Key file.Please check the key file path" + in.getCause());
        } catch (Exception se) {
            log.error(se);
        }
        return null;
    }

    /**
     * RSA 数据签名验证
     *
     * @param signdata （RSA签名数据（base64编码）
     * @param data     （待验证的数据原文）
     * @param pubKey   （RSA公钥数据）
     * @return 返回验证结果（TRUE:验证成功；FALSE:验证失败）
     */
    public boolean verifySignature(String signdata, String data, String pubKey) {
        try {
            byte[] signByte = b64decode(signdata);
            byte[] dataByte = data.getBytes("utf-8");
            PublicKey publicKey = getPublicKey(pubKey);
            signature.initVerify(publicKey);
            signature.update(dataByte);
            return signature.verify(signByte);
        } catch (SignatureException e) {
            log.error(pro.tools.tools.toException(e));
        } catch (Exception e) {
            log.error(pro.tools.tools.toException(e));
        }
        return false;
    }

    /**
     * base64编码
     *
     * @param data
     * @return
     */
    private String b64encode(byte[] data) {
        return new BASE64Encoder().encode(data);
    }

    /**
     * base64解码
     *
     * @param data
     * @return
     */
    private byte[] b64decode(String data) {
        try {
            return new BASE64Decoder().decodeBuffer(data);
        } catch (Exception e) {
            log.error(pro.tools.tools.toException(e));
        }
        return null;
    }

    /**
     * RSA数据加密
     *
     * @param data   （需要加密的数据）
     * @param pubKey （RSA公钥）
     * @return 返回加密后的密文（BASE64编码）
     */
    public String encryptData(String data, String pubKey) {
        try {
            byte[] dataByte = data.getBytes("utf-8");
            PublicKey publicKey = getPublicKey(pubKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] dataReturn = new byte[]{};
            // StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dataByte.length; i += 100) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(dataByte, i, i + 100));
                // sb.append(new String(doFinal));
                dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
            }

            // return b64encode(cipher.doFinal(dataByte));
            return b64encode(dataReturn);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * RSA数据解密
     *
     * @param encryptedData （需要解密的数据base64编码数据）
     * @param priKey        （RSA的私钥）
     * @return 返回解密后的原始明文
     */
    public String decryptData(String encryptedData, String priKey) {
        try {
            byte[] encryData = b64decode(encryptedData);
            PrivateKey privateKey = getPrivateKey(priKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < encryData.length; i += 128) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(encryData, i, i + 128));
                sb.append(new String(doFinal, "utf-8"));
            }

            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成密钥 自动产生RSA1024位密钥
     *
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public void getAutoCreateRSA() throws NoSuchAlgorithmException, IOException {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            PublicKey puk = kp.getPublic();
            PrivateKey prk = kp.getPrivate();

            pubkey = getPubKeyString(puk);
            prikey = getPriKeyString(prk);
        } catch (Exception e) {
            log.error(pro.tools.tools.toException(e));
        }
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getPrikey() {
        return prikey;
    }

    public void setPrikey(String prikey) {
        this.prikey = prikey;
    }

}
