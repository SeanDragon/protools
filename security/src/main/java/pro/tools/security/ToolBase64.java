package pro.tools.security;

import pro.tools.constant.StrConst;
import pro.tools.file.ToolFile;

import java.io.File;
import java.io.IOException;

/**
 * Base64的加密解密
 *
 * @author SeanDragon
 * Create By 2017-04-20 14:43
 */
public final class ToolBase64 {
    private ToolBase64() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 功能：编码字符串
     *
     * @param data
     *         源字符串
     *
     * @return String
     */
    public static String encode(String data) {
        return new String(encode(data.getBytes()));
    }

    /**
     * 功能：解码字符串
     *
     * @param data
     *         源字符串
     *
     * @return String
     */
    public static String decode(String data) {
        return new String(decode(data.toCharArray()));
    }

    /**
     * 功能：编码byte[]
     *
     * @param data
     *         源
     *
     * @return char[]
     */
    public static char[] encode(byte[] data) {
        char[] out = new char[((data.length + 2) / 3) * 4];
        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
            boolean quad = false;
            boolean trip = false;

            int val = (0xFF & (int) data[i]);
            val <<= 8;
            if ((i + 1) < data.length) {
                val |= (0xFF & (int) data[i + 1]);
                trip = true;
            }
            val <<= 8;
            if ((i + 2) < data.length) {
                val |= (0xFF & (int) data[i + 2]);
                quad = true;
            }
            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 1] = alphabet[val & 0x3F];
            val >>= 6;
            out[index] = alphabet[val & 0x3F];
        }
        return out;
    }

    /**
     * 功能：解码
     *
     * @param data
     *         编码后的字符数组
     *
     * @return byte[]
     */
    public static byte[] decode(char[] data) {

        int tempLen = data.length;
        for (int ix = 0; ix < data.length; ix++) {
            if ((data[ix] > 255) || codes[data[ix]] < 0) {
                --tempLen; // ignore non-valid chars and padding
            }
        }

        int len = (tempLen / 4) * 3;
        if ((tempLen % 4) == 3) {
            len += 2;
        }
        if ((tempLen % 4) == 2) {
            len += 1;

        }
        byte[] out = new byte[len];

        int shift = 0;
        int accum = 0;
        int index = 0;

        for (char aData : data) {
            int value = (aData > 255) ? -1 : codes[aData];

            if (value >= 0) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if (shift >= 8) {
                    shift -= 8;
                    out[index++] = (byte) ((accum >> shift) & 0xff);
                }
            }
        }

        if (index != out.length) {
            throw new Error("Miscalculated data length (wrote " + index
                    + " instead of " + out.length + ")");
        }

        return out;
    }

    /**
     * 功能：编码文件
     *
     * @param file
     *         源文件
     */
    public static void encode(File file) throws IOException {
        if (!file.exists()) {
            System.exit(0);
        } else {
            byte[] decoded = ToolFile.readFile2Bytes(file);
            char[] encoded = encode(decoded);
            ToolFile.writeFileFromString(file, new String(encoded), true);
        }
    }

    /**
     * 功能：解码文件。
     *
     * @param file
     *         源文件
     *
     * @throws IOException
     */
    public static void decode(File file) throws IOException {
        if (!file.exists()) {
            System.exit(0);
        } else {
            char[] encoded = ToolFile.readFile2String(file, StrConst.DEFAULT_CHARSET_NAME).toCharArray();
            byte[] decoded = decode(encoded);
            ToolFile.writeFileFromString(file, new String(decoded), true);
        }
    }

    private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
            .toCharArray();

    private static byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++) {
            codes[i] = -1;
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            codes[i] = (byte) (i - 'A');
        }

        for (int i = 'a'; i <= 'z'; i++) {
            codes[i] = (byte) (26 + i - 'a');
        }
        for (int i = '0'; i <= '9'; i++) {
            codes[i] = (byte) (52 + i - '0');
        }
        codes['+'] = 62;
        codes['/'] = 63;
    }

}