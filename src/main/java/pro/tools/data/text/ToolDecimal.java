package pro.tools.data.text;


import pro.tools.decimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created on 17/2/6 09:27 星期五.
 *
 * @author SeanDragon
 */
public class ToolDecimal {
    public static String formatNumber_string(Double value, int scale) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static Double formatNumber_double(Double value, int scale) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //不四舍五入取整
    public static String getWord(Object value) {
        if (ToolStr.isEmpty(null != value ? value.toString() : ""))
            return "0";
        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(0);
        formatter.setGroupingSize(0);
        formatter.setRoundingMode(RoundingMode.FLOOR);
        return formatter.format(value);
    }

    //四舍五入
    public static String getWordF(Object value) {
        if (ToolStr.isEmpty(null != value ? value.toString() : ""))
            return "0";
        return String.format("%.0f", decimal.get(value.toString()));
    }
}
