package pro.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密、解密工具类
 * Created by Administrator on 2016/3/7.
 * @author Steven Duan
 * @version 1.0
 */
public final class security {
    //private final static Logger log = (security.class);
    private final static Logger log = LoggerFactory.getLogger(security.class);
    /**
     * MD5 加密
     * @param str string
     * @return string
     */
    public static String MD5(String str) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try
        {
            byte[] btInput = str.getBytes("UTF-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char strs[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
                strs[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(strs);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

//        if (str == null)
//            return null;
//        try {
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            md5.update(str.getBytes("UTF-8"));
//            byte[] digest = md5.digest();
//            StringBuffer hexString = new StringBuffer();
//            String strTemp;
//            for (int i = 0; i < digest.length; i++) {
//                strTemp = Integer.toHexString((digest[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
//                hexString.append(strTemp);
//            }
//            return hexString.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    // ==Base64加解密==================================================================

    /**
     * Base64加密
     * @param str string
     * @return string
     * @throws UnsupportedEncodingException exception
     */
    public static String Base64Encode(String str) throws UnsupportedEncodingException {
        return new BASE64Encoder().encode(str.getBytes("UTF-8"));
    }

    /**
     * 解密
     */
    public static String Base64Decode(String str) throws IOException {
//		str = str.replaceAll(" ", "+");
        return new String(new BASE64Decoder().decodeBuffer(str), "UTF-8");
    }

    // ==Aes加解密==================================================================

    /**
     * aes解密-128位
     * @param encryptContent string
     * @param password string
     * @return string
     */
    public static String AesDecrypt(String encryptContent, String password) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(hex2Bytes(encryptContent)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * aes加密-128位
     * @param content string
     * @param password string
     * @return string
     */
    public static String AesEncrypt(String content, String password) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return byte2Hex(cipher.doFinal(content.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将byte[] 转换成字符串
     * @param srcBytes byte[]
     * @return string
     */
    public static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }

    /**
     * 将16进制字符串转为转换成字符串
     * @param source string
     * @return byte[]
     */
    public static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

    /**
     * DES加密
     * @param source string
     * @param desKey string
     * @return string
     * @throws Exception
     */
    public static String desEncrypt(String source, String desKey) throws Exception {
        try {
            // 从原始密匙数据创建DESKeySpec对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(new DESKeySpec(desKey.getBytes()));
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            // 现在，获取数据并加密
            byte[] destBytes = cipher.doFinal(source.getBytes());
            StringBuilder hexRetSB = new StringBuilder();
            for (byte b : destBytes) {
                String hexString = Integer.toHexString(0x00ff & b);
                hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
            }
            return hexRetSB.toString();
        } catch (Exception e) {
            throw new Exception("DES加密发生错误", e);
        }
    }

    /**
     * DES解密
     * @param source string
     * @param desKey string
     * @return string
     * @throws Exception
     */
    public static String desDecrypt(String source, String desKey) throws Exception {
        // 解密数据
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(new DESKeySpec(desKey.getBytes()));
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            // 现在，获取数据并解密
            byte[] destBytes = cipher.doFinal(sourceBytes);
            return new String(destBytes);
        } catch (Exception e) {
            throw new Exception("DES解密发生错误", e);
        }
    }

    /**
     * 3DES加密
     * @param src byte[]
     * @param keybyte byte[]
     * @return byte[]
     * @throws Exception
     */
    public static byte[] threeDesEncrypt(byte[] src, byte[] keybyte) throws Exception {
        try {
            // 生成密钥
            byte[] key = new byte[24];
            if (keybyte.length < key.length) {
                System.arraycopy(keybyte, 0, key, 0, keybyte.length);
            } else {
                System.arraycopy(keybyte, 0, key, 0, key.length);
            }
            SecretKey deskey = new SecretKeySpec(key, "DESede");
            // 加密
            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (Exception e) {
            throw new Exception("3DES加密发生错误", e);
        }
    }

    /**
     * 3DES解密
     * @param src byte[]
     * @param keybyte byte[]
     * @return byte[]
     * @throws Exception
     */
    public static byte[] threeDesDecrypt(byte[] src, byte[] keybyte) throws Exception {
        try {
            // 生成密钥
            byte[] key = new byte[24];
            if (keybyte.length < key.length) {
                System.arraycopy(keybyte, 0, key, 0, keybyte.length);
            } else {
                System.arraycopy(keybyte, 0, key, 0, key.length);
            }
            SecretKey deskey = new SecretKeySpec(key, "DESede");
            // 解密
            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (Exception e) {
            throw new Exception("3DES解密发生错误", e);
        }
    }

    /**
     * 3DES加密
     * @param src string
     * @param key string
     * @return string
     * @throws Exception
     */
    public static String threeDesEncrypt(String src, String key) throws Exception {
        return byte2Hex(threeDesEncrypt(src.getBytes(), key.getBytes()));
    }

    /**
     * 3DES加密
     * @param src string
     * @param key string
     * @return string
     * @throws Exception
     */
    public static String threeDesDecrypt(String src, String key) throws Exception {
        return new String(threeDesDecrypt(hex2Bytes(src), key.getBytes()));
    }

    /**
     * sha1加密
     * @param str string
     * @return string
     */
    public static String SHA1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * main
     * @param args string[]
     * @throws Exception
     */
//    public static void main(String[] args) throws Exception {
//        String str = "数据加密的基本过程就是对原来为明文的文件或数据按某种算法进行处理，使其成为不可读的一段代码，通常称为“密文”，"
//                + "使其只能在输入相应的密钥之后才能显示出本来内容，通过这样的途径来达到保护数据不被非法人窃取、阅读的目的。 "
//                + "该过程的逆过程为解密，即将该编码信息转化为其原来数据的过程。";
//        str+=str;str+=str;str+=str;
//        String PWD = "SecurityUtil.PWD";
//        System.out.println("原文:[" + str.length() + "]" + str);
//        System.out.println("==MD5===============");
//        System.out.println(MD5(str));
//        System.out.println("==Base64============");
//        String strBase64 = Base64Encode(str);
//        System.out.println("加密:[" + strBase64.length() + "]" + strBase64);
//        System.out.println("解密:" + Base64Decode(strBase64));
//        System.out.println("==Aes============");
//        String strAes = AesEncrypt(str, PWD);
//        System.out.println("加密:[" + strAes.length() + "]" + strAes);
//        System.out.println("解密:" + AesDecrypt(strAes, PWD));
//        System.out.println("==Des==============");
//        String strDes = desEncrypt(str, PWD);
//        System.out.println("加密:[" + strDes.length() + "]" + strDes);
//        System.out.println("解密:" + desDecrypt(strDes, PWD));
//        System.out.println("==3Des==============");
//        String str3Des = threeDesEncrypt(str, PWD);
//        System.out.println("加密:[" + str3Des.length() + "]" + str3Des);
//        System.out.println("解密:" + threeDesDecrypt(str3Des, PWD));
//
//        //==========================================
//
//        long t1=System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            MD5(str);
//        System.out.println("\nMD5:"+(System.currentTimeMillis()-t1));
//        t1=System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            Base64Encode(str);
//        System.out.println("Base64:"+(System.currentTimeMillis()-t1));
//        t1=System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            AesEncrypt(str, PWD);
//        System.out.println("Aes:"+(System.currentTimeMillis()-t1));
//        t1=System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            desEncrypt(str, PWD);
//        System.out.println("Des:"+(System.currentTimeMillis()-t1));
//        t1=System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            threeDesEncrypt(str, PWD);
//        System.out.println("3Des:"+(System.currentTimeMillis()-t1));
//        //=======================================
//        t1=System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            Base64Decode(strBase64);
//        System.out.println("\nBase64:"+(System.currentTimeMillis()-t1));
//        t1=System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            AesDecrypt(strAes, PWD);
//        System.out.println("Aes:"+(System.currentTimeMillis()-t1));
//        t1=System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            desDecrypt(strDes, PWD);
//        System.out.println("Des:"+(System.currentTimeMillis()-t1));
//        t1=System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++)
//            threeDesDecrypt(str3Des, PWD);
//        System.out.println("3Des:"+(System.currentTimeMillis()-t1));
//
//
//    }


    /**
     * SHA1 加密类
     * Created by Administrator on 2016/3/7.
     * @author Steven Duan
     * @version 1.0
     */
    public class SHA1 {
        /**
         * int[]
         */
        private final int[] abcde = {
                0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476, 0xc3d2e1f0
        };
        /**
         * 摘要数据存储数组
         */
        private int[] digestInt = new int[5];
        /**
         * 计算过程中的临时数据存储数组
         */
        private int[] tmpData = new int[80];

        /**
         * 计算sha-1摘要
         * @param bytedata byte[]
         * @return int
         */
        private int process_input_bytes(byte[] bytedata) {
            // 初试化常量
            System.arraycopy(abcde, 0, digestInt, 0, abcde.length);
            // 格式化输入字节数组，补10及长度数据
            byte[] newbyte = byteArrayFormatData(bytedata);
            // 获取数据摘要计算的数据单元个数
            int MCount = newbyte.length / 64;
            // 循环对每个数据单元进行摘要计算
            for (int pos = 0; pos < MCount; pos++) {
                // 将每个单元的数据转换成16个整型数据，并保存到tmpData的前16个数组元素中
                for (int j = 0; j < 16; j++) {
                    tmpData[j] = byteArrayToInt(newbyte, (pos * 64) + (j * 4));
                }
                // 摘要计算函数
                encrypt();
            }
            return 20;
        }

        /**
         * 格式化输入字节数组格式
         * @param bytedata byte[]
         * @return byte[]
         */
        private byte[] byteArrayFormatData(byte[] bytedata) {
            // 补0数量
            int zeros = 0;
            // 补位后总位数
            int size = 0;
            // 原始数据长度
            int n = bytedata.length;
            // 模64后的剩余位数
            int m = n % 64;
            // 计算添加0的个数以及添加10后的总长度
            if (m < 56) {
                zeros = 55 - m;
                size = n - m + 64;
            } else if (m == 56) {
                zeros = 63;
                size = n + 8 + 64;
            } else {
                zeros = 63 - m + 56;
                size = (n + 64) - m + 64;
            }
            // 补位后生成的新数组内容
            byte[] newbyte = new byte[size];
            // 复制数组的前面部分
            System.arraycopy(bytedata, 0, newbyte, 0, n);
            // 获得数组Append数据元素的位置
            int l = n;
            // 补1操作
            newbyte[l++] = (byte) 0x80;
            // 补0操作
            for (int i = 0; i < zeros; i++) {
                newbyte[l++] = (byte) 0x00;
            }
            // 计算数据长度，补数据长度位共8字节，长整型
            long N = (long) n * 8;
            byte h8 = (byte) (N & 0xFF);
            byte h7 = (byte) ((N >> 8) & 0xFF);
            byte h6 = (byte) ((N >> 16) & 0xFF);
            byte h5 = (byte) ((N >> 24) & 0xFF);
            byte h4 = (byte) ((N >> 32) & 0xFF);
            byte h3 = (byte) ((N >> 40) & 0xFF);
            byte h2 = (byte) ((N >> 48) & 0xFF);
            byte h1 = (byte) (N >> 56);
            newbyte[l++] = h1;
            newbyte[l++] = h2;
            newbyte[l++] = h3;
            newbyte[l++] = h4;
            newbyte[l++] = h5;
            newbyte[l++] = h6;
            newbyte[l++] = h7;
            newbyte[l++] = h8;
            return newbyte;
        }

        /**
         * f1
         * @param x int
         * @param y int
         * @param z int
         * @return int
         */
        private int f1(int x, int y, int z) {
            return (x & y) | (~x & z);
        }

        /**
         * f2
         * @param x int
         * @param y int
         * @param z int
         * @return int
         */
        private int f2(int x, int y, int z) {
            return x ^ y ^ z;
        }

        /**
         * f3
         * @param x int
         * @param y int
         * @param z int
         * @return int
         */
        private int f3(int x, int y, int z) {
            return (x & y) | (x & z) | (y & z);
        }

        /**
         * f4
         * @param x int
         * @param y int
         * @return int
         */
        private int f4(int x, int y) {
            return (x << y) | x >>> (32 - y);
        }

        /**
         * 单元摘要计算函数
         */
        private void encrypt() {
            for (int i = 16; i <= 79; i++) {
                tmpData[i] = f4(tmpData[i - 3] ^ tmpData[i - 8] ^ tmpData[i - 14] ^
                        tmpData[i - 16], 1);
            }
            int[] tmpabcde = new int[5];
            for (int i1 = 0; i1 < tmpabcde.length; i1++) {
                tmpabcde[i1] = digestInt[i1];
            }
            for (int j = 0; j <= 19; j++) {
                int tmp = f4(tmpabcde[0], 5) +
                        f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                        tmpData[j] + 0x5a827999;
                tmpabcde[4] = tmpabcde[3];
                tmpabcde[3] = tmpabcde[2];
                tmpabcde[2] = f4(tmpabcde[1], 30);
                tmpabcde[1] = tmpabcde[0];
                tmpabcde[0] = tmp;
            }
            for (int k = 20; k <= 39; k++) {
                int tmp = f4(tmpabcde[0], 5) +
                        f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                        tmpData[k] + 0x6ed9eba1;
                tmpabcde[4] = tmpabcde[3];
                tmpabcde[3] = tmpabcde[2];
                tmpabcde[2] = f4(tmpabcde[1], 30);
                tmpabcde[1] = tmpabcde[0];
                tmpabcde[0] = tmp;
            }
            for (int l = 40; l <= 59; l++) {
                int tmp = f4(tmpabcde[0], 5) +
                        f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                        tmpData[l] + 0x8f1bbcdc;
                tmpabcde[4] = tmpabcde[3];
                tmpabcde[3] = tmpabcde[2];
                tmpabcde[2] = f4(tmpabcde[1], 30);
                tmpabcde[1] = tmpabcde[0];
                tmpabcde[0] = tmp;
            }
            for (int m = 60; m <= 79; m++) {
                int tmp = f4(tmpabcde[0], 5) +
                        f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                        tmpData[m] + 0xca62c1d6;
                tmpabcde[4] = tmpabcde[3];
                tmpabcde[3] = tmpabcde[2];
                tmpabcde[2] = f4(tmpabcde[1], 30);
                tmpabcde[1] = tmpabcde[0];
                tmpabcde[0] = tmp;
            }
            for (int i2 = 0; i2 < tmpabcde.length; i2++) {
                digestInt[i2] = digestInt[i2] + tmpabcde[i2];
            }
            for (int n = 0; n < tmpData.length; n++) {
                tmpData[n] = 0;
            }
        }

        /**
         * 4字节数组转换为整数
         * @param bytedata byte[]
         * @param i int
         * @return int
         */
        private int byteArrayToInt(byte[] bytedata, int i) {
            return ((bytedata[i] & 0xff) << 24) | ((bytedata[i + 1] & 0xff) << 16) |
                    ((bytedata[i + 2] & 0xff) << 8) | (bytedata[i + 3] & 0xff);
        }

        /**
         * 整数转换为4字节数组
         * @param intValue int
         * @param byteData byte[]
         * @param i int
         */
        private void intToByteArray(int intValue, byte[] byteData, int i) {
            byteData[i] = (byte) (intValue >>> 24);
            byteData[i + 1] = (byte) (intValue >>> 16);
            byteData[i + 2] = (byte) (intValue >>> 8);
            byteData[i + 3] = (byte) intValue;
        }

        /**
         * 将字节转换为十六进制字符串 static
         * @param ib byte
         * @return string
         */
        private String byteToHexString(byte ib) {
            char[] Digit = {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                    'D', 'E', 'F'
            };
            char[] ob = new char[2];
            ob[0] = Digit[(ib >>> 4) & 0X0F];
            ob[1] = Digit[ib & 0X0F];
            String s = new String(ob);
            return s;
        }

        /**
         * 将字节数组转换为十六进制字符串 static
         * @param bytearray byte[]
         * @return string
         */
        private String byteArrayToHexString(byte[] bytearray) {
            String strDigest = "";
            for (int i = 0; i < bytearray.length; i++) {
                strDigest += byteToHexString(bytearray[i]);
            }
            return strDigest;
        }

        /**
         * 计算sha-1摘要，返回相应的字节数组
         * @param byteData byte[]
         * @return byte[]
         */
        public byte[] getDigestOfBytes(byte[] byteData) {
            process_input_bytes(byteData);
            byte[] digest = new byte[20];
            for (int i = 0; i < digestInt.length; i++) {
                intToByteArray(digestInt[i], digest, i * 4);
            }
            return digest;
        }

        /**
         * 计算sha-1摘要，返回相应的十六进制字符串
         * @param byteData byte[]
         * @return string
         */
        public String getDigestOfString(byte[] byteData) {
            return byteArrayToHexString(getDigestOfBytes(byteData));
        }

//        /**
//         * main
//         * @param args string[]
//         */
//        public static void main(String[] args) {
//            String data = "123456";
//            System.out.println(data);
//            String digest = new SHA1().getDigestOfString(data.getBytes());
//            System.out.println(digest);
//
//            // System.out.println( ToMD5.convertSHA1(data).toUpperCase());
//        }
    }
}
