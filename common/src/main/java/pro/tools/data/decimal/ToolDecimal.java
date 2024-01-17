package pro.tools.data.decimal;

import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * 浮点数特殊方法工具
 *
 * @author SeanDragon
 * Create By 2017-04-13 15:29
 */
public final class ToolDecimal {

    private ToolDecimal() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    private final static BigInteger HUNDRED = BigInteger.valueOf(100);

    /**
     * 自己实现的开平方
     *
     * @param number
     *         数值
     * @param scale
     *         精度
     * @param roundingMode
     *         舍入方法
     *
     * @return 结果
     */
    static BigDecimal sqrt(BigDecimal number, int scale, RoundingMode roundingMode) {
        if (number.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("sqrt with negative");
        }
        BigInteger integer = number.toBigInteger();
        StringBuilder sb = new StringBuilder();
        String strInt = integer.toString();
        int lenInt = strInt.length();
        if (lenInt % 2 != 0) {
            strInt = '0' + strInt;
            lenInt++;
        }
        BigInteger res = BigInteger.ZERO;
        BigInteger rem = BigInteger.ZERO;
        for (int i = 0; i < lenInt / 2; i++) {
            res = res.multiply(BigInteger.TEN);
            rem = rem.multiply(HUNDRED);

            BigInteger temp = new BigInteger(strInt.substring(i * 2, i * 2 + 2));
            rem = rem.add(temp);

            BigInteger j = BigInteger.TEN;
            while (j.compareTo(BigInteger.ZERO) > 0) {
                j = j.subtract(BigInteger.ONE);
                if (((res.add(j)).multiply(j)).compareTo(rem) <= 0) {
                    break;
                }
            }

            res = res.add(j);
            rem = rem.subtract(res.multiply(j));
            res = res.add(j);
            sb.append(j);
        }
        sb.append('.');
        BigDecimal fraction = number.subtract(number.setScale(0, RoundingMode.DOWN));
        int fractionLength = (fraction.scale() + 1) / 2;
        fraction = fraction.movePointRight(fractionLength * 2);
        String fractionStr = fraction.toPlainString();
        for (int i = 0; i <= scale; i++) {
            res = res.multiply(BigInteger.TEN);
            rem = rem.multiply(HUNDRED);

            if (i < fractionLength) {
                BigInteger temp = new BigInteger(fractionStr.substring(i * 2, i * 2 + 2));
                rem = rem.add(temp);
            }

            BigInteger j = BigInteger.TEN;
            while (j.compareTo(BigInteger.ZERO) > 0) {
                j = j.subtract(BigInteger.ONE);
                if (((res.add(j)).multiply(j)).compareTo(rem) <= 0) {
                    break;
                }
            }
            res = res.add(j);
            rem = rem.subtract(res.multiply(j));
            res = res.add(j);
            sb.append(j);
        }
        return new BigDecimal(sb.toString()).setScale(scale, roundingMode);
    }

    /**
     * 返回零位的最高位(“最左侧”)之前的数中指定的int值的二进制补码表示一比特
     * <p>
     * 此方法返回零位的最高位(“最左侧”)前在指定的int值的二进制补码表示法，或32个1位的数量，如果该值为零。
     *
     * @param i
     *         数字
     *
     * @return 结果
     */
    public static int numberOfLeadingZeros(long i) {
        if (i == 0) {
            return 64;
        }
        int n = 1;
        int x = (int) (i >>> 32);
        if (x == 0) {
            n += 32;
            x = (int) i;
        }
        if (x >>> 16 == 0) {
            n += 16;
            x <<= 16;
        }
        if (x >>> 24 == 0) {
            n += 8;
            x <<= 8;
        }
        if (x >>> 28 == 0) {
            n += 4;
            x <<= 4;
        }
        if (x >>> 30 == 0) {
            n += 2;
            x <<= 2;
        }
        n -= x >>> 31;
        return n;
    }

    /**
     * 设置给DecimalFormat用的精度字符创
     *
     * @param scale
     *         精度
     *
     * @return 结果
     */
    public static String scale2FormatStr(int scale) {
        StringBuilder mStrformat = new StringBuilder("##0");
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (scale > 0) {
            mStrformat.append(".");
            for (int i = 0; i < scale; i++) {
                mStrformat.append("0");
            }
        }

        return mStrformat.toString();
    }

    private static final RoundingMode DEFAULT_RM = RoundingMode.HALF_EVEN;
    private static final Map<String, DecimalFormat> DECIMAL_FORMAT_MAP = Maps.newConcurrentMap();

    public static DecimalFormat scale2Format(final int scale) {
        return scale2Format(scale, DEFAULT_RM);
    }

    public static DecimalFormat scale2Format(final int scale, final RoundingMode roundingMode) {
        final String cacheKey = scale + roundingMode.name();
        DecimalFormat result = DECIMAL_FORMAT_MAP.get(cacheKey);
        if (result == null) {
            result = new DecimalFormat(scale2FormatStr(scale));
            result.setRoundingMode(roundingMode);
            DECIMAL_FORMAT_MAP.put(cacheKey, result);
        }
        return result;
    }

    /**
     * @param value
     * @param scale
     *
     * @return
     */
    public static String number2Str(Double value, int scale) {
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).toString();
    }

    /**
     * @param value
     * @param scale
     *
     * @return
     */
    public static Double number2double(Double value, int scale) {
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }
}
