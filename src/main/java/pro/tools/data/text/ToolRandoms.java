package pro.tools.data.text;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 随机数类
 */
public final class ToolRandoms {
    private ToolRandoms() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    // 定义验证码字符.去除了O、I、l、、等容易混淆的字母
    public static final char authCodeAll[] = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'a', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            '3', '4', '5', '7', '8'};
    // 定义验证码数字
    private static final char authCodeNumber[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final int authCodeAllLength = authCodeAll.length;
    public static final int authCodeNumberLength = authCodeNumber.length;
    private static final SecureRandom random = new SecureRandom();

    private final static char[] digits = {//32位
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    }, digits_$ = {//64位
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '*', '$'
    };

    /**
     * 生成验证码
     *
     * @return
     */
    public static char getAuthCodeAllChar() {
        return authCodeAll[numberRandom(0, authCodeAllLength)];
    }

    /**
     * 生成验证码，纯数字
     *
     * @return
     */
    public static String getAuthCodeNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(authCodeNumber[numberRandom(0, length)]);
        }
        return sb.toString();
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public static String getAuthCodeAll(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(authCodeAll[numberRandom(0, length)]);
        }
        return sb.toString();
    }

    /**
     * 获取UUID by jdk
     *
     * @return
     */
    public static String getUuid(boolean is32bit) {
        String uuid = UUID.randomUUID().toString();
        if (is32bit) {
            return uuid.replace("-", "");
        }
        return uuid;
    }

    /**
     * 产生两个数之间的随机数
     *
     * @param min 小数
     * @param max 比min大的数
     * @return int 随机数字
     */
    public static int numberRandom(int min, int max) {
        return min + random.nextInt(max - min);
    }

    /**
     * 产生0--number的随机数,不包括num
     *
     * @param number 数字
     * @return int 随机数字
     */
    public static int numberRandom(int number) {
        return random.nextInt(number);
    }

    /**
     * 生成RGB随机数
     *
     * @return
     */
    public static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }


    public static String getRandomStrByNanoTime(final boolean shift) {
        return toUnsignedString(getRightNanoTime() + pro.tools.tools.getCode(5), shift ? 6 : 4);
    }

    private static long getRightNanoTime() {
        long nanoTime = System.nanoTime();
        if (nanoTime < 0) return getRightNanoTime();
        else return nanoTime;
    }

    /**
     * shift :
     * 5   32进制
     * 6   64进制
     * 放入long类型数字
     *
     * @param i     数字
     * @param shift 2的几次幂
     * @return 经过转换的
     */
    private static String toUnsignedString(long i, int shift) {
        shift = shift > 6 ? 6 : shift;
        final char[] self = shift > 5 ? digits_$ : digits;
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        do {
            buf[--charPos] = self[(int) (i & mask)];
            i >>>= shift;
        } while (i != 0);
        return new String(buf, charPos, (64 - charPos));
    }
}
