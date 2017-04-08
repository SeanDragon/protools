package pro.tools.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.time.ToolDateTime;

import java.math.BigDecimal;

public class ToolTypeConverter {

    private static final Logger log = LoggerFactory.getLogger(ToolTypeConverter.class);

    /**
     * 数据类型解析
     *
     * @param type
     * @param value
     * @return
     */
    public static Object dataParse(Class<?> type, String value) {
        if (type == String.class) {
            return ("".equals(value) ? null : value);    // 用户在表单域中没有输入内容时将提交过来 "", 因为没有输入,所以要转成 null.
        }
        value = value.trim();
        if ("".equals(value)) {    // 前面的 String跳过以后,所有的空字符串全都转成 null,  这是合理的
            return null;
        }

        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        }

        if (type == java.math.BigInteger.class) {
            return new java.math.BigInteger(value);
        }

        if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        }

        if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        }

        if (type == float.class || type == Float.class) {
            return Float.parseFloat(value);
        }

        if (type == short.class || type == Short.class) {
            return Short.parseShort(value);
        }

        if (type == BigDecimal.class) {
            return new BigDecimal(value);
        }

        if (type == boolean.class || type == Boolean.class) {
            value = value.toLowerCase();
            if ("1".equals(value) || "true".equals(value)) {
                return Boolean.TRUE;
            } else if ("0".equals(value) || "false".equals(value)) {
                return Boolean.FALSE;
            }
        }

        if (type == char.class || type == Character.class) {
            return value.toCharArray()[0];
        }

        if (type == java.util.Date.class) {
            return parseDate(value);
        }

        if (type == java.sql.Date.class) {
            return new java.sql.Date(parseDate(value).getTime());
        }

        if (type == java.sql.Time.class) {
            return java.sql.Time.valueOf(value);
        }

        if (type == java.sql.Timestamp.class) {
            return new java.sql.Timestamp(parseDate(value).getTime());
        }

        if (type == byte[].class) {
            return value.getBytes();
        }

        log.error("没有解析到有效字段类型");

        return null;
    }

    /**
     * 解析为java.util.Date，如果需要其他日期类型，再转
     *
     * @param value
     * @return
     */
    private static java.util.Date parseDate(String value) {
        int dateLength = value.length();
        switch (dateLength) {
            case ToolDateTime.pattern_ym_length:
                return ToolDateTime.parse(value, ToolDateTime.pattern_ym);

            case ToolDateTime.pattern_ymd_length:
                return ToolDateTime.parse(value, ToolDateTime.pattern_ymd);

            case ToolDateTime.pattern_ymd_hm_length:
                return ToolDateTime.parse(value, ToolDateTime.pattern_ymd_hm);

            case ToolDateTime.pattern_ymd_hms_length:
                return ToolDateTime.parse(value, ToolDateTime.pattern_ymd_hms);

            case ToolDateTime.pattern_ymd_hms_s_length:
                return ToolDateTime.parse(value, ToolDateTime.pattern_ymd_hms_s);

            default:
                log.debug("没有解析到有效字段日期长度类型");
                return null;
        }
    }

}
