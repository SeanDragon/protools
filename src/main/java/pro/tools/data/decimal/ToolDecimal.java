package pro.tools.data.decimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * 浮点数特殊方法工具
 *
 * @author SeanDragon
 *         Create By 2017-04-13 15:29
 */
public class ToolDecimal {
    private final static BigInteger HUNDRED = BigInteger.valueOf(100);

    /**
     * 自己实现的开平方
     *
     * @param number       数值
     * @param scale        精度
     * @param roundingMode 舍入方法
     * @return 结果
     */
    public static BigDecimal sqrt(BigDecimal number, int scale, RoundingMode roundingMode) {
        if (number.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("sqrt with negative");
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
        BigDecimal fraction = number.subtract(number.setScale(0, BigDecimal.ROUND_DOWN));
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

    public static int numberOfLeadingZeros(long i) {
        // HD, Figure 5-6
        if (i == 0)
            return 64;
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
     * @param scale 精度
     * @return 结果
     */
    public static String scale2FormatStr(int scale) {
        StringBuilder m_strFormat = new StringBuilder("##0");
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (scale > 0) {
            m_strFormat.append(".");
            for (int i = 0; i < scale; i++) {
                m_strFormat.append("0");
            }
        }

        return m_strFormat.toString();
    }
}
