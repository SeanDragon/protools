package pro.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式化工具，数字、字符串、Object类型之间的常用转换
 * Created by Administrator on 2016/3/7.
 * @author Steven Duan
 * @version 1.0
 */
public final class format {
    /**
     * 符串首字母大写变小写
     * @return 返回转换后的字符串
     */
    public static String firstLower(){
        char[] chars = new char[1];
        String str = "ABCDE1234";
        chars[0] = str.charAt(0);
        String temp = new String(chars);
        if (chars[0] >= 'A' && chars[0] <= 'Z')
            return str.replaceFirst(temp, temp.toLowerCase());
        return str;
    }

    /**
     * 判断字符串是否为数字
     * @param str 需要判断的字符串
     * @return 是返回true, 不是返回false
     */
    public static boolean isNumber(String str){
        Pattern pattern = Pattern.compile("^([-]){0,1}([0-9]){1,}([.]){0,1}([0-9]){0,}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /** ==============IS Base=================== */
    /**
     * 判断是否为整数(包括负数)
     * @param arg 要判断的数字
     * @return 是返回true, 否则返回false
     */
    public static boolean isNumber(Object arg) {
        return NumberBo(0, toString(arg));
    }

    /**
     * 判断是否为小数(包括整数,包括负数)
     * @param arg 要判断的数字
     * @return 是返回true, 否则返回false
     */
    public static boolean isDecimal(Object arg) {
        return NumberBo(1, toString(arg));
    }

    /**
     * 判断是否为空 ,
     * @param arg 要判断的值
     * @return 为空返回true, 否则返回false
     */
    public static boolean isEmpty(Object arg) {
        return toStringTrim(arg).length() == 0;
    }

    /**
     * 判断是否为数字或英文字符
     * @param str 需要判断的字符串，不能含有标点或其它特殊字符
     * @return 若为数字或英文字符返回true 否则返回false
     */
    public static boolean isWordOrNumber(String str) {
        //可部分匹配
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        str.matches("");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否为姓名字符
     * @param name 需要判断的字符串，不能含有标点或其它特殊字符
     * @return 符合姓名规则返回true 否则返回false
     */
    public static boolean isName(String name){
        Pattern p = Pattern.compile("^[A-Za-z\u0391-\uFFE5]{1,20}$");//("^([A-Za-z\u0391-\uFFE5]){2,20}$");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 判断是否为中文
     * @param word 需要判断的字符串
     * @return 中文返回true 否则返回false
     */
    public static boolean isChinease(String word){
        Pattern p = Pattern.compile("^[\u0391-\uFFE5]{1,50}$");//("^([A-Za-z\u0391-\uFFE5]){2,20}$");
        Matcher m = p.matcher(word);
        return m.matches();
    }

    /**
     * 判断是否为手机号码
     * @param mobile 需要判断的字符串，不能含有标点或其它特殊字符
     * @return 符合手机号码规则返回true 否则返回false
     */
    public static boolean isPhoneNumber(String mobile) {
        //可部分匹配
        Pattern p = Pattern.compile("^[1][0-9][0-9]{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /** ==============TO Base=================== */
    /**
     * Object 转换成 Int 转换失败 返回默认值 0, 使用:toInt(值,默认值[选填])
     * @param args 参数序列
     * @return 返回转换后的int
     */
    public static int toInt(Object... args) {
        int def = 0;
        if (args != null) {
            String str = toStringTrim(args[0]);
            // 判断小数情况。舍弃小数位
            int stri = str.indexOf('.');
            str = stri > 0 ? str.substring(0, stri) : str;
            if (args.length > 1)
                def = Integer.parseInt(args[args.length - 1].toString());
            if (isNumber(str))
                return Integer.parseInt(str);
        }
        return def;
    }

    /**
     * Object 转换成 Long 转换失败 返回默认值 0,使用:toLong(值,默认值[选填])
     * @param args 参数序列
     * @return 返回转换后的long
     */
    public static long toLong(Object... args) {
        Long def = 0L;
        if (args != null) {
            String str = toStringTrim(args[0]);
            if (args.length > 1)
                def = Long.parseLong(args[args.length - 1].toString());
            if (isNumber(str))
                return Long.parseLong(str);
        }
        return def;
    }

    /**
     * Object 转换成 Double 转换失败 返回默认值 0,使用:toDouble(值,默认值[选填])
     * @param args 参数序列
     * @return 返回转换后的double
     */
    public static double toDouble(Object... args) {
        double def = 0;
        if (args != null) {
            String str = toStringTrim(args[0]);
            if (args.length > 1)
                def = Double.parseDouble(args[args.length - 1].toString());
            if (isDecimal(str))
                return Double.parseDouble(str);
        }
        return def;
    }

    /**
     * Object 转换成 BigDecimal 转换失败 返回默认值 0,使用:toDecimal(值,默认值[选填]) 特别注意: new BigDecimal(Double) 会有误差，得先转String
     * @param args 参数序列
     * @return 返回转换后的BigDecimal
     */
    public static BigDecimal toDecimal(Object... args) {
        return new BigDecimal(Double.toString(toDouble(args)));
    }

    /**
     * Object 转换成 Boolean 转换失败 返回默认值 false,使用:toBoolean(值,默认值[选填])
     * @param bool 参数
     * @return 返回转换后的Boolean
     */
    public static boolean toBoolean(String bool) {
        return !(isEmpty(bool) || (!bool.equals("1") && !bool.equalsIgnoreCase("true") && !bool.equalsIgnoreCase("ok")));
    }

    /**
     * Object 转换成 String 为null 返回空字符,使用:toString(值,默认值[选填])
     * @param args 参数序列
     * @return 返回转换后的String
     */
    public static String toString(Object... args) {
        String def = "";
        if (args != null) {
            if (args.length > 1)
                def = toString(args[args.length - 1]);
            Object obj = args[0];
            if (obj == null)
                return def;
            return obj.toString();
        } else {
            return def;
        }
    }

    /**
     * Object 转换成 String[去除所以空格]; 为null 返回空字符,使用:toStringTrim(值,默认值[选填])
     * @param args 参数序列
     * @return 返回转换后的String
     */
    public static String toStringTrim(Object... args) {
        String str = toString(args);
        return str.replaceAll("\\s*", "");
    }

    /**
     * 数字左边补0
     * @param obj 要补0的数
     * @param length 补0后的长度
     * @return 返回补0后的String
     */
    public static String leftPad(Object obj, int length) {
        return String.format("%0" + length + "d", toInt(obj));
    }

    /**
     * 小数 转 百分数
     * @param str 要转成百分数的小数
     * @return 返回转换后的String
     */
    public static String toPercent(Double str) {
        StringBuffer sb = new StringBuffer(Double.toString(str * 100.0000d));
        return sb.append("%").toString();
    }

    /**
     * 百分数 转 小数
     * @param str 要转成小数的百分数
     * @return 返回转换后的Double
     */
    public static Double toPercent2(String str) {
        if (str.charAt(str.length() - 1) == '%')
            return Double.parseDouble(str.substring(0, str.length() - 1)) / 100.0000d;
        return 0d;
    }

    /**
     * 将byte[] 转换成字符串
     * @param srcBytes 要转换的byte[]
     * @return 返回转换后的String
     */
    public static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }

    /**
     * 将字符串转为转换成16进制字符串
     * @param source 要转换的字符串
     * @return 返回转换后的byte[]
     */
    public static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

    /**
     * String 转 Money
     * @param str 要转换的数字
     * @param MoneyType 要转换的格式
     * @return 返回转换后的String
     */
    public static String toMoney(Object str, String MoneyType) {
        DecimalFormat df = new DecimalFormat(MoneyType);
        if (isDecimal(str))
            return df.format(toDecimal(str)).toString();
        return df.format(toDecimal("0.00")).toString();
    }

    /**
     * 获取字符串str 左边len位数
     * @param obj 字符串
     * @param len 位数
     * @return 返回剪切后的字符串
     */
    public static String getLeft(Object obj, int len) {
        String str = toString(obj);
        if (len <= 0)
            return "";
        if (str.length() <= len)
            return str;
        else
            return str.substring(0, len);
    }

    /**
     * 获取字符串str 右边len位数
     * @param obj 字符串
     * @param len 位数
     * @return 返回剪切后的字符串
     */
    public static String getRight(Object obj, int len) {
        String str = toString(obj);
        if (len <= 0)
            return "";
        if (str.length() <= len)
            return str;
        else
            return str.substring(str.length() - len, str.length());
    }

    /**
     * 首字母变小写
     * @param str 要转换的字符串
     * @return 返回转换后的字符串
     */
    public static String firstCharToLowerCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toLowerCase(firstChar) + tail;
        return str;
    }

    /**
     * 首字母变大写
     * @param str 要转换的字符串
     * @return 返回转换后的字符串
     */
    public static String firstCharToUpperCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toUpperCase(firstChar) + tail;
        return str;
    }

    /**
     * List集合去除重复值 只能用于基本数据类型，对象类集合，自己写
     * @param list 要处理的List
     * @return 返回处理后的List
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List delMoreList(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }

    /** ============== END =================== */

    /**
     * 判断是否为数字
     * @param type 判断类型, 整数为0, 小数为1
     * @param obj 要判断的数字
     * @return 是返回true, 否则返回false
     */
    private static boolean NumberBo(int type, Object obj) {
        if (isEmpty(obj))
            return false;
        int points = 0;
        int chr = 0;
        String str = toString(obj);
        for (int i = str.length(); --i >= 0;) {
            chr = str.charAt(i);
            if (chr < 48 || chr > 57) { // 判断数字
                if (i == 0 && chr == 45) // 判断 - 号
                    return true;
                if (i >= 0 && chr == 46 && type == 1) { // 判断 . 号
                    ++points;
                    if (points <= 1)
                        continue;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 金额格式
     */
    public static class MoneyType {
        /**
         * 保留2位有效数字，整数位每3位逗号隔开 （默认）
         */
        public static final String DECIMAL = "#,##0.00";
        /**
         * 保留2位有效数字
         */
        public static final String DECIMAL_2 = "0.00";
        /**
         * 保留4位有效数字
         */
        public static final String DECIMAL_4 = "0.0000";
    }

}