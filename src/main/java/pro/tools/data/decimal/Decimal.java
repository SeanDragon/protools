package pro.tools.data.decimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 数值类型封装类
 *
 * @author SeanDragon
 *         Create By 2017-04-13 14:22
 */
public class Decimal extends Number {

    //region 全局变量
    private BigDecimal bigDecimal;
    private MathContext defaultMathContext = new MathContext(0, RoundingMode.HALF_EVEN);
    //endregion

    //region 初始化模块
    public Decimal(Object initValue) {
        init(initValue);
    }

    public Decimal(Object initValue, MathContext mathContext) {
        init(initValue);
        this.defaultMathContext = mathContext;
    }

    private void init(Object initValue) {
        if (initValue instanceof Decimal) {
            Decimal value = (Decimal) initValue;
            bigDecimal = value.getBigDecimal();
        } else if (initValue instanceof BigDecimal) {
            double v = ((BigDecimal) initValue).doubleValue();
            bigDecimal = new BigDecimal(v, defaultMathContext);
        } else if (initValue instanceof BigInteger) {
            double v = ((BigInteger) initValue).doubleValue();
            bigDecimal = new BigDecimal(v, defaultMathContext);
        } else if (initValue instanceof Number) {
            Number value = (Number) initValue;
            bigDecimal = new BigDecimal(value.doubleValue(), defaultMathContext);
        } else if (initValue instanceof String) {
            bigDecimal = new BigDecimal(String.valueOf(initValue), defaultMathContext);
        } else {
            throw new NumberFormatException();
        }
    }

    //region 便利生成方式,获取实例方法
    public static Decimal instance(Object initValue) {
        return new Decimal(initValue);
    }

    public static Decimal instance(Object initValue, MathContext mathContext) {
        return new Decimal(initValue, mathContext);
    }
    //endregion
    //endregion

    //region 获取属性值
    public BigDecimal getBigDecimal() {
        return this.bigDecimal;
    }

    public MathContext getDefaultMathContext() {
        return this.defaultMathContext;
    }

    /**
     * 获取该数被除后的整数
     *
     * @param object 因数
     * @return 结果
     */
    public Decimal getDivGetInteger(Object object) {
        return new Decimal(this.bigDecimal.divideToIntegralValue(new Decimal(object).getBigDecimal(), defaultMathContext));
    }

    /**
     * 求余
     *
     * @param object 因数
     * @return 结果
     */
    public Decimal getRemainder(Object object) {
        return new Decimal(this.bigDecimal.remainder(new Decimal(object).getBigDecimal(), defaultMathContext));
    }
    //endregion

    //region 基本数值运算
    public Decimal add(Object object) {
        this.bigDecimal = this.bigDecimal.add(new Decimal(object).getBigDecimal(), defaultMathContext);
        return this;
    }

    public Decimal sub(Object object) {
        this.bigDecimal = this.bigDecimal.subtract(new Decimal(object).getBigDecimal(), defaultMathContext);
        return this;
    }

    public Decimal mul(Object object) {
        this.bigDecimal = this.bigDecimal.multiply(new Decimal(object).getBigDecimal(), defaultMathContext);
        return this;
    }

    public Decimal div(Object object) {
        this.bigDecimal = this.bigDecimal.divide(new Decimal(object).getBigDecimal(), defaultMathContext);
        return this;
    }

    public Decimal abs() {
        this.bigDecimal = this.bigDecimal.abs(defaultMathContext);
        return this;
    }
    //endregion

    //region 复杂数值运算

    /**
     * 幂运算
     *
     * @param n 幂数
     * @return 结果
     */
    public Decimal pow(int n) {
        this.bigDecimal = this.bigDecimal.pow(n, defaultMathContext);
        return this;
    }

    /**
     * 开平方
     *
     * @param scale 精度
     * @return 结果
     */
    public Decimal sqrt2(int scale) {
        if (this.bigDecimal.divide(new BigDecimal(1), defaultMathContext).doubleValue() == 1.00D || this.bigDecimal.divide(new BigDecimal(2), defaultMathContext).doubleValue() == 1.00D) {
            this.bigDecimal = new BigDecimal(intValue());
        }
        if (scale > 13) {
            this.bigDecimal = ToolDecimal.sqrt(this.bigDecimal, scale, defaultMathContext.getRoundingMode());
        } else {
            double sqrt = Math.sqrt(this.bigDecimal.doubleValue());
            this.bigDecimal = new BigDecimal(sqrt, defaultMathContext);
        }
        return this;
    }

    /**
     * 开N次方
     *
     * @param n 几次方
     * @return 结果
     */
    public Decimal sqrtN(int n) {
        double log = Math.pow(this.doubleValue(), 1D / n);
        this.bigDecimal = new BigDecimal(log, defaultMathContext);
        return this;
    }
    //endregion

    //region 数据变现
    @Override
    public String toString() {
        return moneyStrValue();
    }

    public String fullStrValue() {
        return this.bigDecimal.toPlainString();
    }

    public String moneyStrValue() {
        return String.valueOf(moneyValue());
    }

    public double moneyValue() {
        return doubleValue(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public int intValue() {
        return (int) doubleValue();
        //return this.bigDecimal.intValueExact();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
        //return this.bigDecimal.longValueExact();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return this.bigDecimal.doubleValue();
    }

    /**
     * 传入进度和舍入原则进行double
     *
     * @param scale        进度
     * @param roundingMode 舍入原则
     * @return 结果
     */
    public double doubleValue(int scale, RoundingMode roundingMode) {
        DecimalFormat decimalFormat = new DecimalFormat(ToolDecimal.scale2FormatStr(scale));
        decimalFormat.setRoundingMode(roundingMode);//设置舍入算法
        return Double.valueOf(decimalFormat.format(doubleValue()));
    }
    //endregion
}
