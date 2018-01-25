package pro.tools.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * 密码加密类
 * Create At 2017/03/14
 *
 * @author SeanDragon
 */
public final class ToolPBE2 {
    private ToolPBE2() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * 盐的长度
     */
    private static final int SALT_BYTE_SIZE = 32 / 2;

    /**
     * 生成密文的长度
     */
    private static final int HASH_BIT_SIZE = 128 * 4;

    /**
     * 迭代次数
     */
    private static final int PBKDF2_ITERATIONS = 1000;

    /**
     * 对输入的密码进行验证
     *
     * @param attemptedPassword
     *         待验证的密码
     * @param encryptedPassword
     *         密文
     * @param salt
     *         盐值
     *
     * @return 是否验证成功
     *
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        // 用相同的盐值对用户输入的密码进行加密
        String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return encryptedAttemptedPassword.equals(encryptedPassword);
    }

    /**
     * 生成密文
     *
     * @param password
     *         明文密码
     * @param salt
     *         盐值
     *
     * @return
     *
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String getEncryptedPassword(String password, String salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), fromHex(salt), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
        SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return toHex(f.generateSecret(spec).getEncoded());
    }

    /**
     * 通过提供加密的强随机数生成器 生成盐
     *
     * @return
     *
     * @throws NoSuchAlgorithmException
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return toHex(salt);
    }

    /**
     * 十六进制字符串转二进制字符串
     *
     * @param hex
     *         the hex string
     *
     * @return the hex string decoded into a byte array
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * 二进制字符串转十六进制字符串
     *
     * @param array
     *         the byte array to ToolJson
     *
     * @return a length*2 character string encoding the byte array
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}