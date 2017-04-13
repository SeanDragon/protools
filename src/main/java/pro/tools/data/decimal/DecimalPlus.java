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
public final class DecimalPlus {

    private BigDecimal bigDecimal;
    private MathContext defaultMathContext = MathContext.UNLIMITED;

    //region 初始化模块
    public DecimalPlus(Object initValue) {
        init(initValue);
    }

    public DecimalPlus(Object initValue, MathContext mathContext) {
        init(initValue);
        this.defaultMathContext = mathContext;
    }

    private void init(Object initValue) {
        if (initValue instanceof DecimalPlus) {
            DecimalPlus value = (DecimalPlus) initValue;
            bigDecimal = value.getBigDecimal();
        } else if (initValue instanceof BigDecimal) {
            double v = ((BigDecimal) initValue).doubleValue();
            bigDecimal = new BigDecimal(v, defaultMathContext);
        } else if (initValue instanceof BigInteger) {
            double v = ((BigInteger) initValue).doubleValue();
            bigDecimal = new BigDecimal(v, defaultMathContext);
        } else if (initValue instanceof String ||
                initValue instanceof Long || initValue instanceof Byte ||
                initValue instanceof Float || initValue instanceof Short ||
                initValue instanceof Double || initValue instanceof Integer) {
            bigDecimal = new BigDecimal(String.valueOf(initValue), defaultMathContext);
        } else {
            throw new NumberFormatException();
        }
    }
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
    public DecimalPlus getDivGetInteger(Object object) {
        return new DecimalPlus(this.bigDecimal.divideToIntegralValue(new DecimalPlus(object).getBigDecimal(), defaultMathContext));
    }

    /**
     * 求余
     *
     * @param object 因数
     * @return 结果
     */
    public DecimalPlus getRemainder(Object object) {
        return new DecimalPlus(this.bigDecimal.remainder(new DecimalPlus(object).getBigDecimal(), defaultMathContext));
    }
    //endregion

    //region 基本数值运算
    public DecimalPlus add(Object object) {
        this.bigDecimal = this.bigDecimal.add(new DecimalPlus(object).getBigDecimal(), defaultMathContext);
        return this;
    }

    public DecimalPlus sub(Object object) {
        this.bigDecimal = this.bigDecimal.subtract(new DecimalPlus(object).getBigDecimal(), defaultMathContext);
        return this;
    }

    public DecimalPlus mul(Object object) {
        this.bigDecimal = this.bigDecimal.multiply(new DecimalPlus(object).getBigDecimal(), defaultMathContext);
        return this;
    }

    public DecimalPlus div(Object object) {
        this.bigDecimal = this.bigDecimal.divide(new DecimalPlus(object).getBigDecimal(), defaultMathContext);
        return this;
    }

    public DecimalPlus abs() {
        this.bigDecimal = this.bigDecimal.abs(defaultMathContext);
        return this;
    }
    //endregion

    public DecimalPlus pow(int n) {
        this.bigDecimal = this.bigDecimal.pow(n, defaultMathContext);
        return this;
    }

    public DecimalPlus sqrt(int scale) {
        if (scale > 13) {
            this.bigDecimal = ToolDecimal.sqrt(this.bigDecimal, 500, RoundingMode.HALF_EVEN);
        } else {
            double sqrt = Math.sqrt(this.bigDecimal.doubleValue());
            this.bigDecimal = new BigDecimal(sqrt, defaultMathContext);
        }
        return this;
    }

    @Override
    public String toString() {
        return toMoney();
    }

    public String toRealString() {
        return this.bigDecimal.toPlainString();
    }

    public String toMoney() {
        return String.valueOf(toDouble(2, RoundingMode.HALF_EVEN));
    }

    /**
     * 传入进度和舍入原则进行double
     *
     * @param scale        进度
     * @param roundingMode 舍入原则
     * @return 结果
     */
    public double toDouble(int scale, RoundingMode roundingMode) {
        DecimalFormat decimalFormat = new DecimalFormat(ToolDecimal.scale2FormatStr(scale));
        decimalFormat.setRoundingMode(roundingMode);//设置银行家算法
        return Double.valueOf(decimalFormat.format(this.bigDecimal.doubleValue()));
    }

    public double toDouble() {
        return this.bigDecimal.doubleValue();
    }

    public int toInt() {
        return this.bigDecimal.intValueExact();
    }

    public long toLong() {
        return this.bigDecimal.longValueExact();
    }
}
