package pro.tools.constant;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 正则的常量
 *
 * @author SeanDragon Create By 2017-04-17 10:13
 */
public final class RegexConst {

    /**
     * 正则：金额
     */
    public static final Pattern REGEX_MONEY = compile("^(([1-9]\\d*)|0)(\\.\\d{1,2})?$");
    /**
     * 正则：英文单词或者数字
     */
    public static final Pattern REGEX_WORD_OR_NUMBER = compile("^[A-Za-z0-9]+$");
    /**
     * 正则：手机号（简单）
     */
    public static final Pattern REGEX_MOBILE_SIMPLE = compile("^[1]\\d{10}$");
    /**
     * 正则：手机号（精准）
     * 13 14 15 16 17 18 19 开头都可以
     */
    public static final Pattern REGEX_MOBILE_EXACT = compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
    // public static final Pattern REGEX_MOBILE_EXACT = compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$");
    /**
     * 正则：电话号码
     */
    public static final Pattern REGEX_TEL = compile("^0\\d{2,3}[- ]?\\d{7,8}");
    /**
     * 正则：身份证号码15位
     */
    public static final Pattern REGEX_ID_CARD15 = compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
    /**
     * 正则：身份证号码18位
     */
    public static final Pattern REGEX_ID_CARD18 = compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$");
    /**
     * 正则：邮箱
     */
    public static final Pattern REGEX_EMAIL = compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    /**
     * 正则：URL
     */
    public static final Pattern REGEX_URL = compile("^(http|https)://.*$");
    //public static final Pattern REGEX_URL = compile("[a-zA-z]+://[^\\s]*");
    /**
     * 正则：汉字
     */
    public static final Pattern REGEX_ZH = compile("^[\\u4e00-\\u9fa5]+$");
    /**
     * 正则：用户名，取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
     */
    public static final Pattern REGEX_USERNAME = compile("^[A-Za-z\\u4e00-\\u9fa5]{4,30}(?<!_)$");
    /**
     * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
     */
    public static final Pattern REGEX_DATE = compile("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$");
    /**
     * 正则：IP地址
     */
    public static final Pattern REGEX_IP = compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
    /**
     * 正则：双字节字符(包括汉字在内)
     */
    public static final Pattern REGEX_DOUBLE_BYTE_CHAR = compile("[^\\x00-\\xff]");
    /**
     * 正则：空白行
     */
    public static final Pattern REGEX_BLANK_LINE = compile("\\n\\s*\\r");
    /**
     * 正则：QQ号
     */
    public static final Pattern REGEX_QQ_NUM = compile("[1-9][0-9]{4,}");
    /**
     * 正则：中国邮政编码
     */
    public static final Pattern REGEX_ZIP_CODE = compile("[1-9]\\d{5}(?!\\d)");

    //region 以下摘自http://tool.oschina.net/regex
    /**
     * 正则：正整数
     */
    public static final Pattern REGEX_POSITIVE_INTEGER = compile("^[1-9]\\d*$");
    /**
     * 正则：负整数
     */
    public static final Pattern REGEX_NEGATIVE_INTEGER = compile("^-[1-9]\\d*$");
    /**
     * 正则：整数
     */
    public static final Pattern REGEX_INTEGER = compile("^-?[1-9]\\d*$");
    /**
     * 正则：非负整数(正整数 + 0)
     */
    public static final Pattern REGEX_NOT_NEGATIVE_INTEGER = compile("^[1-9]\\d*|0$");
    /**
     * 正则：非正整数（负整数 + 0）
     */
    public static final Pattern REGEX_NOT_POSITIVE_INTEGER = compile("^-[1-9]\\d*|0$");
    /**
     * 正则：正浮点数
     */
    public static final Pattern REGEX_POSITIVE_FLOAT = compile("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$");
    /**
     * 正则：负浮点数
     */
    public static final Pattern REGEX_NEGATIVE_FLOAT = compile("^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$");

    private RegexConst() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    //endregion

}
