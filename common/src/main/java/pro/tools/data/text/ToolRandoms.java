package pro.tools.data.text;

import java.security.SecureRandom;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * 随机数类
 *
 * @author SeanDragon
 */
public final class ToolRandoms {
    private ToolRandoms() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 定义验证码字符.去除了O、I、l、、等容易混淆的字母
     */
    private static final char[] AUTH_CODE_ALL = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'a', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            '3', '4', '5', '7', '8'};

    /**
     * 定义验证码数字
     */
    private static final char[] AUTH_CODE_NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final int AUTH_CODE_ALL_LENGTH = AUTH_CODE_ALL.length;
    private static final int AUTH_CODE_NUMBER_LENGTH = AUTH_CODE_NUMBER.length;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final static char[] DIGITS = {    //32位
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
            'Y', 'Z', '_', '_'
    };

    /**
     * 生成验证码
     */
    public static char getAuthCodeAllChar() {
        return AUTH_CODE_ALL[getNumberRandom(0, AUTH_CODE_ALL_LENGTH)];
    }

    /**
     * 生成验证码，纯数字
     */
    public static String getAuthCodeNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(AUTH_CODE_NUMBER[getNumberRandom(0, AUTH_CODE_NUMBER_LENGTH)]);
        }
        return sb.toString();
    }

    /**
     * 生成验证码
     */
    public static String getAuthCodeAll(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(AUTH_CODE_ALL[getNumberRandom(0, AUTH_CODE_ALL_LENGTH)]);
        }
        return sb.toString();
    }

    /**
     * 获取UUID by jdk
     */
    public static String getUuid(boolean is32bit) {
        String uuid = getUuid().toString();
        if (is32bit) {
            return uuid.replace("-", "");
        }
        return uuid;
    }

    /**
     * 获取原生UUID对象
     */
    public static UUID getUuid() {
        return UUID.randomUUID();
    }

    /**
     * 产生两个数之间的随机数
     *
     * @param min
     *         小数
     * @param max
     *         比min大的数
     *
     * @return int 随机数字
     */
    public static int getNumberRandom(int min, int max) {
        return min + RANDOM.nextInt(max - min);
    }

    /**
     * 产生0--number的随机数,不包括num
     *
     * @param number
     *         数字
     *
     * @return int 随机数字
     */
    public static int getNumberRandom(int number) {
        return RANDOM.nextInt(number);
    }

    /**
     * 生成RGB随机数
     *
     * @return
     */
    public static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = RANDOM.nextInt(255);
        }
        return rgb;
    }

    /**
     * 通过nanotime获取随机数
     *
     * @param shift
     *
     * @return
     */
    public static String getRandomStrByNanoTime(final boolean shift) {
        return toUnsignedString(getRightNanoTime() + getNumberRandom(5), shift ? 6 : 4);
    }

    /**
     * 通过UUID和nanotime共同获取随机数
     *
     * @return
     */
    public static String getRandomStr() {
        String sum = ToolRandoms.getUuid(false);
        StringBuilder result = new StringBuilder();

        StringTokenizer stringTokenizer = new StringTokenizer(sum, "-");
        while (stringTokenizer.hasMoreTokens()) {
            Long newLongToken = Long.parseLong(stringTokenizer.nextToken(), 16);
            result.append(ToolRandoms.toUnsignedString(newLongToken, 6));
        }

        return result.toString();
    }

    /**
     * 获取正确的nanotime
     *
     * @return
     */
    public static long getRightNanoTime() {
        long nanoTime = System.nanoTime();
        if (nanoTime < 0) {
            return getRightNanoTime();
        } else {
            return nanoTime;
        }
    }

    /**
     * shift : 5   32进制 6   64进制 放入long类型数字
     *
     * @param i
     *         数字
     * @param shift
     *         2的几次幂
     *
     * @return 经过转换的
     */
    public static String toUnsignedString(long i, int shift) {
        shift = shift > 6 ? 6 : shift;
        final char[] self = shift > 5 ? digits_$ : DIGITS;
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
