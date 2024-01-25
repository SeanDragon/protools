package pro.tools.data.text;

import com.google.common.collect.Maps;
import pro.tools.constant.UnitConst;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 转换相关工具
 *
 * @author SeanDragon
 */
public final class ToolConvert {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private ToolConvert() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * byteArr转hexString <p>例如：</p> bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes
     *         字节数组
     *
     * @return 16进制大写字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        if (len <= 0) {
            return null;
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >>> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * hexString转byteArr <p>例如：</p> hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString
     *         十六进制字符串
     *
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (ToolStr.isSpace(hexString)) {
            return null;
        }
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * hexChar转int
     *
     * @param hexChar
     *         hex单个字节
     *
     * @return 0..15
     */
    private static int hex2Dec(char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * charArr转byteArr
     *
     * @param chars
     *         字符数组
     *
     * @return 字节数组
     */
    public static byte[] chars2Bytes(char[] chars) {
        if (chars == null || chars.length <= 0) {
            return null;
        }
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * byteArr转charArr
     *
     * @param bytes
     *         字节数组
     *
     * @return 字符数组
     */
    public static char[] bytes2Chars(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        if (len <= 0) {
            return null;
        }
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * 以unit为单位的内存大小转字节数
     *
     * @param memorySize
     *         大小
     * @param unit
     *         单位类型 <ul> <li>{@link UnitConst.MemoryUnit#BYTE}: 字节</li> <li>{@link UnitConst.MemoryUnit#KB}  : 千字节</li>
     *         <li>{@link UnitConst.MemoryUnit#MB}  : 兆</li> <li>{@link UnitConst.MemoryUnit#GB}  : GB</li> </ul>
     *
     * @return 字节数
     */
    public static long memorySize2Byte(long memorySize, UnitConst.MemoryUnit unit) {
        if (memorySize < 0) {
            return -1;
        }
        switch (unit) {
            default:
            case BYTE:
                return memorySize;
            case KB:
                return memorySize * UnitConst.KB;
            case MB:
                return memorySize * UnitConst.MB;
            case GB:
                return memorySize * UnitConst.GB;
        }
    }

    /**
     * 字节数转以unit为单位的内存大小
     *
     * @param byteNum
     *         字节数
     * @param unit
     *         单位类型 <ul> <li>{@link UnitConst.MemoryUnit#BYTE}: 字节</li> <li>{@link UnitConst.MemoryUnit#KB}  : 千字节</li>
     *         <li>{@link UnitConst.MemoryUnit#MB}  : 兆</li> <li>{@link UnitConst.MemoryUnit#GB}  : GB</li> </ul>
     *
     * @return 以unit为单位的size
     */
    public static double byte2MemorySize(long byteNum, UnitConst.MemoryUnit unit) {
        if (byteNum < 0) {
            return -1;
        }
        switch (unit) {
            default:
            case BYTE:
                return (double) byteNum;
            case KB:
                return (double) byteNum / UnitConst.KB;
            case MB:
                return (double) byteNum / UnitConst.MB;
            case GB:
                return (double) byteNum / UnitConst.GB;
        }
    }

    /**
     * 字节数转合适内存大小 <p>保留3位小数</p>
     *
     * @param byteNum
     *         字节数
     *
     * @return 合适内存大小
     */
    public static String byte2FitMemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < UnitConst.KB) {
            return String.format("%.3fB", byteNum + 0.0005);
        } else if (byteNum < UnitConst.MB) {
            return String.format("%.3fKB", byteNum / UnitConst.KB + 0.0005);
        } else if (byteNum < UnitConst.GB) {
            return String.format("%.3fMB", byteNum / UnitConst.MB + 0.0005);
        } else {
            return String.format("%.3fGB", byteNum / UnitConst.GB + 0.0005);
        }
    }

    /**
     * 以unit为单位的时间长度转毫秒时间戳
     *
     * @param timeSpan
     *         毫秒时间戳
     * @param unit
     *         单位类型 <ul> <li>{@link UnitConst.TimeUnit#MSEC}: 毫秒</li> <li>{@link UnitConst.TimeUnit#SEC }: 秒</li>
     *         <li>{@link UnitConst.TimeUnit#MIN }: 分</li> <li>{@link UnitConst.TimeUnit#HOUR}: 小时</li> <li>{@link
     *         UnitConst.TimeUnit#DAY }: 天</li> </ul>
     *
     * @return 毫秒时间戳
     */
    public static long timeSpan2Millis(long timeSpan, UnitConst.TimeUnit unit) {
        switch (unit) {
            default:
            case MSEC:
                return timeSpan;
            case SEC:
                return timeSpan * UnitConst.SEC;
            case MIN:
                return timeSpan * UnitConst.MIN;
            case HOUR:
                return timeSpan * UnitConst.HOUR;
            case DAY:
                return timeSpan * UnitConst.DAY;
        }
    }

    /**
     * 毫秒时间戳转以unit为单位的时间长度
     *
     * @param millis
     *         毫秒时间戳
     * @param unit
     *         单位类型 <ul> <li>{@link UnitConst.TimeUnit#MSEC}: 毫秒</li> <li>{@link UnitConst.TimeUnit#SEC }: 秒</li>
     *         <li>{@link UnitConst.TimeUnit#MIN }: 分</li> <li>{@link UnitConst.TimeUnit#HOUR}: 小时</li> <li>{@link
     *         UnitConst.TimeUnit#DAY }: 天</li> </ul>
     *
     * @return 以unit为单位的时间长度
     */
    public static long millis2TimeSpan(long millis, UnitConst.TimeUnit unit) {
        switch (unit) {
            default:
            case MSEC:
                return millis;
            case SEC:
                return millis / UnitConst.SEC;
            case MIN:
                return millis / UnitConst.MIN;
            case HOUR:
                return millis / UnitConst.HOUR;
            case DAY:
                return millis / UnitConst.DAY;
        }
    }

    /**
     * 毫秒时间戳转合适时间长度
     *
     * @param millis
     *         毫秒时间戳 <p>小于等于0，返回null</p>
     * @param precision
     *         精度 <ul> <li>precision = 0，返回null</li> <li>precision = 1，返回天</li> <li>precision = 2，返回天和小时</li>
     *         <li>precision = 3，返回天、小时和分钟</li> <li>precision = 4，返回天、小时、分钟和秒</li> <li>precision &gt;=
     *         5，返回天、小时、分钟、秒和毫秒</li> </ul>
     *
     * @return 合适时间长度
     */
    public static String millis2FitTimeSpan(long millis, int precision) {
        if (millis <= 0 || precision <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        precision = Math.min(precision, 5);
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    /**
     * bytes转bits
     *
     * @param bytes
     *         字节数组
     *
     * @return bits
     */
    public static String bytes2Bits(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    /**
     * bits转bytes
     *
     * @param bits
     *         二进制
     *
     * @return bytes
     */
    public static byte[] bits2Bytes(String bits) {
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // 不是8的倍数前面补0
        if (lenMod != 0) {
            StringBuilder bitsBuilder = new StringBuilder(bits);
            for (int i = lenMod; i < 8; i++) {
                bitsBuilder.insert(0, "0");
            }
            bits = bitsBuilder.toString();
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * inputStream转outputStream
     *
     * @param is
     *         输入流
     *
     * @return outputStream子类
     */
    public static ByteArrayOutputStream input2OutputStream(InputStream is) throws IOException {
        if (is == null) {
            return null;
        }
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            byte[] b = new byte[UnitConst.KB];
            int len;
            while ((len = is.read(b, 0, UnitConst.KB)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        }
    }

    /**
     * inputStream转byteArr
     *
     * @param is
     *         输入流
     *
     * @return 字节数组
     */
    public static byte[] inputStream2Bytes(InputStream is) throws IOException {
        if (is == null) {
            return null;
        }
        return input2OutputStream(is).toByteArray();
    }

    /**
     * byteArr转inputStream
     *
     * @param bytes
     *         字节数组
     *
     * @return 输入流
     */
    public static InputStream bytes2InputStream(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        return new ByteArrayInputStream(bytes);
    }

    /**
     * outputStream转byteArr
     *
     * @param out
     *         输出流
     *
     * @return 字节数组
     */
    public static byte[] outputStream2Bytes(OutputStream out) {
        if (out == null) {
            return null;
        }
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * outputStream转byteArr
     *
     * @param bytes
     *         字节数组
     *
     * @return 字节数组
     */
    public static OutputStream bytes2OutputStream(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            os.write(bytes);
            return os;
        }
    }

    /**
     * inputStream转string按编码
     *
     * @param is
     *         输入流
     * @param charsetName
     *         编码格式
     *
     * @return 字符串
     */
    public static String inputStream2String(InputStream is, String charsetName) throws IOException {
        if (is == null || ToolStr.isSpace(charsetName)) {
            return null;
        }
        return new String(inputStream2Bytes(is), charsetName);
    }

    /**
     * string转inputStream按编码
     *
     * @param string
     *         字符串
     * @param charsetName
     *         编码格式
     *
     * @return 输入流
     */
    public static InputStream string2InputStream(String string, String charsetName) throws UnsupportedEncodingException {
        if (string == null || ToolStr.isSpace(charsetName)) {
            return null;
        }

        return new ByteArrayInputStream(string.getBytes(charsetName));

    }

    /**
     * outputStream转string按编码
     *
     * @param out
     *         输出流
     * @param charsetName
     *         编码格式
     *
     * @return 字符串
     */
    public static String outputStream2String(OutputStream out, String charsetName) throws UnsupportedEncodingException {
        if (out == null || ToolStr.isSpace(charsetName)) {
            return null;
        }

        return new String(outputStream2Bytes(out), charsetName);

    }

    /**
     * string转outputStream按编码
     *
     * @param string
     *         字符串
     * @param charsetName
     *         编码格式
     *
     * @return 输入流
     */
    public static OutputStream string2OutputStream(String string, String charsetName) throws IOException {
        if (string == null || ToolStr.isSpace(charsetName)) {
            return null;
        }
        return bytes2OutputStream(string.getBytes(charsetName));
    }

    /**
     * outputStream转inputStream
     *
     * @param out
     *         输出流
     *
     * @return inputStream子类
     */
    public static ByteArrayInputStream output2InputStream(OutputStream out) {
        if (out == null) {
            return null;
        }
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    public static Map<String, String> str2map(String str, String tokenStr, String splitStr) {
        StringTokenizer tokenSTK = new StringTokenizer(str, tokenStr);
        Map<String, String> result = Maps.newHashMapWithExpectedSize(tokenSTK.countTokens());
        while (tokenSTK.hasMoreTokens()) {
            String nextStr = tokenSTK.nextToken();
            StringTokenizer splitSTK = new StringTokenizer(nextStr, splitStr);
            if (splitSTK.countTokens() == 2) {
                result.put(splitSTK.nextToken(), splitSTK.nextToken());
            }
        }
        return result;
    }

    public static String map2str(Map<String, Object> params, String middleStr, String endStr) {
        StringBuilder result = new StringBuilder();
        params.forEach((key, value) -> {
            result.append(key).append(middleStr).append(value.toString()).append(endStr);
        });
        return result.toString();
    }
}
