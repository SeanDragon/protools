package pro.tools.future;


/**
 * Created on 17/3/9 13:26 星期日.
 *
 * @author sd
 */
public class ToolPrimaryKey {

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
