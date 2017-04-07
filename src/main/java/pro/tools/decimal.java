package pro.tools;

import java.math.BigDecimal;

/**
 * 用BigDecimal精确计算Double类型
 * 如果需要精确计算,用String来够造BigDecimal
 * Created by Administrator on 2016/3/7.
 * @author Steven Duan
 * @version 1.0
 */
public final class decimal {
    /*
      由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
      确的浮点数运算，包括加减乘除和四舍五入。
     */
    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 小数位数 精度
     */
    private static final int DEF_SCALE = 6;

    /**
     * RoundingMode 舍入模式
     */
    private static final int RoundingMode = BigDecimal.ROUND_HALF_EVEN;

    /**
     * 这个类不能实例化
     */
    private decimal() {
    }

    /**
     * 将double转成BigDecimal
     * @param v1 double
     * @return BigDecimal
     */
    public static BigDecimal get(double v1){
        return new BigDecimal(v1);
    }

    /**
     * 将String 转成BigDecimal
     * @param v1 String
     * @return BigDecimal
     */
    public static BigDecimal get(String v1){
        return new BigDecimal(v1);
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(double v1, double v2) {
        return add(Double.toString(v1),Double.toString(v2));
    }

    public static BigDecimal add(String v1, String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.add(b2).setScale(DEF_SCALE,RoundingMode);
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(double v1, double v2) {
        return sub(Double.toString(v1),Double.toString(v2));
    }
    public static BigDecimal sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).setScale(DEF_SCALE,RoundingMode);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(double v1, double v2) {
        return mul(Double.toString(v1),Double.toString(v2));
    }
    public static BigDecimal mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(DEF_SCALE,RoundingMode);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }
    public static BigDecimal div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(double v1, double v2, int scale) {
        return div(Double.toString(v1),Double.toString(v2),scale);
    }
    public static BigDecimal div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v1);
        BigDecimal one = new BigDecimal(v2);
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).setScale(DEF_SCALE,RoundingMode);
    }
}