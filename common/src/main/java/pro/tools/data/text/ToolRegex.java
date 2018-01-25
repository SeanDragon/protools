package pro.tools.data.text;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pro.tools.constant.RegexConst.REGEX_DATE;
import static pro.tools.constant.RegexConst.REGEX_EMAIL;
import static pro.tools.constant.RegexConst.REGEX_ID_CARD15;
import static pro.tools.constant.RegexConst.REGEX_ID_CARD18;
import static pro.tools.constant.RegexConst.REGEX_IP;
import static pro.tools.constant.RegexConst.REGEX_MOBILE_EXACT;
import static pro.tools.constant.RegexConst.REGEX_MOBILE_SIMPLE;
import static pro.tools.constant.RegexConst.REGEX_MONEY;
import static pro.tools.constant.RegexConst.REGEX_TEL;
import static pro.tools.constant.RegexConst.REGEX_URL;
import static pro.tools.constant.RegexConst.REGEX_USERNAME;
import static pro.tools.constant.RegexConst.REGEX_WORD_OR_NUMBER;
import static pro.tools.constant.RegexConst.REGEX_ZH;


/**
 * 正则相关工具
 *
 * @author SeanDragon
 */
public final class ToolRegex {

    private ToolRegex() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 判断是否为整数(包括负数)
     *
     * @param input
     *         要判断的数字
     *
     * @return 是返回true, 否则返回false
     */
    public static boolean isInteger(CharSequence input) {
        return isNumber(0, input);
    }

    /**
     * 判断是否为小数(包括整数,包括负数)
     *
     * @param input
     *         要判断的数字
     *
     * @return 是返回true, 否则返回false
     */
    public static boolean isDecimal(CharSequence input) {
        return isNumber(1, input);
    }


    /**
     * 判断是否为数字
     *
     * @param type
     *         判断类型, 整数为0, 小数为1
     * @param str
     *         要判断的数字
     *
     * @return 是返回true, 否则返回false
     */
    private static boolean isNumber(int type, CharSequence str) {
        if (ToolStr.isBlank(str.toString())) {
            return false;
        }
        int points = 0;
        int chr;
        for (int i = str.length(); --i >= 0; ) {
            chr = str.charAt(i);
            // 判断数字
            if (chr < 48 || chr > 57) {
                // 判断 - 号
                if (i == 0 && chr == 45) {
                    return true;
                }
                // 判断 . 号
                if (i >= 0 && chr == 46 && type == 1) {
                    if (++points <= 1) {
                        continue;
                    }
                }
                return false;
            }
        }
        return true;
    }

    private static final Pattern HIDE_MOBILE_PATTERN = Pattern.compile("^[0-9]{11}$");
    private static final String HIDE_MOBILE_REGULAR = "(?<=\\d{3})\\d(?=\\d{4})";

    /**
     * 隐藏手机号中间的4位
     *
     * @param phone
     *
     * @return
     */
    public static String hideMobile(String phone) {
        Matcher matcher = HIDE_MOBILE_PATTERN.matcher(phone);
        if (matcher.matches()) {
            return phone.replaceAll(HIDE_MOBILE_REGULAR, "*");
        }
        return phone;
    }

    /**
     * 判断是否为金额
     *
     * @param input
     *         需要判断的数字
     *
     * @return 若是金额返回true, 否则返回false
     */
    public static boolean isMoney(CharSequence input) {
        return isMatch(REGEX_MONEY, input);
    }

    /**
     * 判断是否为数字或英文字符
     *
     * @param input
     *         需要判断的字符串，不能含有标点或其它特殊字符
     *
     * @return 若为数字或英文字符返回true 否则返回false
     */
    public static boolean isWordOrNumber(CharSequence input) {
        return isMatch(REGEX_WORD_OR_NUMBER, input);
    }

    /**
     * 验证手机号（简单）
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证手机号（精确）
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch(REGEX_MOBILE_EXACT, input);
    }

    /**
     * 验证电话号码
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isTel(CharSequence input) {
        return isMatch(REGEX_TEL, input);
    }

    /**
     * 验证身份证号码15位
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(CharSequence input) {
        return isMatch(REGEX_ID_CARD15, input);
    }

    /**
     * 验证身份证号码18位
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(CharSequence input) {
        return isMatch(REGEX_ID_CARD18, input);
    }

    /**
     * 验证邮箱
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(CharSequence input) {
        return isMatch(REGEX_EMAIL, input);
    }

    /**
     * 验证URL
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(CharSequence input) {
        return isMatch(REGEX_URL, input);
    }

    /**
     * 验证汉字
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isZh(CharSequence input) {
        return isMatch(REGEX_ZH, input);
    }

    /**
     * 验证用户名 <p>取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位</p>
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUsername(CharSequence input) {
        return isMatch(REGEX_USERNAME, input);
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isDate(CharSequence input) {
        return isMatch(REGEX_DATE, input);
    }

    /**
     * 验证IP地址
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(CharSequence input) {
        return isMatch(REGEX_IP, input);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex
     *         正则表达式
     * @param input
     *         要匹配的字符串
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 判断是否匹配正则
     *
     * @param pattern
     *         已经提前编译好的Pattern
     * @param input
     *         要匹配的字符串
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(Pattern pattern, CharSequence input) {
        return input != null && input.length() > 0 && pattern.matcher(input).matches();
    }

    /**
     * 获取正则匹配的部分
     *
     * @param regex
     *         正则表达式
     * @param input
     *         要匹配的字符串
     *
     * @return 正则匹配的部分
     */
    public static List<String> getMatches(String regex, CharSequence input) {
        if (input == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        List<String> matches = Lists.newArrayList();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param input
     *         要分组的字符串
     * @param regex
     *         正则表达式
     *
     * @return 正则匹配分组
     */
    public static String[] getSplits(String input, String regex) {
        if (input == null) {
            return null;
        }
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param input
     *         要替换的字符串
     * @param regex
     *         正则表达式
     * @param replacement
     *         代替者
     *
     * @return 替换正则匹配的第一部分
     */
    public static String getReplaceFirst(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input
     *         要替换的字符串
     * @param regex
     *         正则表达式
     * @param replacement
     *         代替者
     *
     * @return 替换所有正则匹配的部分
     */
    public static String getReplaceAll(String input, String regex, String replacement) {
        if (input == null) {
            return null;
        }
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}