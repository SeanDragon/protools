package pro.tools.data.text;

import java.util.Objects;

/**
 * 字符串相关工具
 *
 * @author SeanDragon
 */
public final class ToolStr {

    private ToolStr() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }


    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s
     *         待校验字符串
     *
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s
     *         待校验字符串
     *
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }


    /**
     * 字符串为 null 或者内部字符全部为 ' ' '\t' '\n' '\r' 这四类字符时返回 true
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int len = str.length();
        if (len == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            switch (str.charAt(i)) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    //case '\b':
                    //case '\f':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串不为空返回true
     *
     * @param str
     *
     * @return
     */
    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断多个字符串不为空字符串返回true
     *
     * @param strings
     *
     * @return
     */
    public static boolean notBlank(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个Object对象不为null，则返回true
     *
     * @param paras
     *
     * @return
     */
    public static boolean notNull(Object... paras) {
        if (paras == null) {
            return false;
        }
        for (Object obj : paras) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a
     *         待校验字符串a
     * @param b
     *         待校验字符串b
     *
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a
     *         待校验字符串a
     * @param b
     *         待校验字符串b
     *
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (Objects.equals(a, b)) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s
     *         待转字符串
     *
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s
     *         字符串
     *
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s
     *         待转字符串
     *
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isBlank(s)) {
            return "";
        }

        char[] chars = s.toCharArray();
        if (Character.isUpperCase(chars[0])) {
            return s;
        }

        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * 首字母小写
     *
     * @param s
     *         待转字符串
     *
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isBlank(s)) {
            return "";
        }

        char[] chars = s.toCharArray();
        if (Character.isLowerCase(chars[0])) {
            return s;
        }

        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * 变全大写
     *
     * @param s
     *
     * @return
     */
    public static String toUpper(String s) {
        if (isBlank(s)) {
            return "";
        }

        return s.toUpperCase();
    }

    /**
     * 变全小写
     *
     * @param s
     *
     * @return
     */
    public static String toLower(String s) {
        if (isBlank(s)) {
            return "";
        }

        return s.toLowerCase();
    }

    /**
     * 反转字符串
     *
     * @param s
     *         待反转字符串
     *
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len == 0) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s
     *         待转字符串
     *
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s
     *         待转字符串
     *
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 将带（下划线）_的字符串格式化驼峰命名法
     *
     * @param stringWithUnderline
     *
     * @return
     */
    public static String toCamelCase(String stringWithUnderline) {
        if (stringWithUnderline.indexOf('_') == -1) {
            return stringWithUnderline;
        }

        stringWithUnderline = stringWithUnderline.toLowerCase();
        char[] fromArray = stringWithUnderline.toCharArray();
        char[] toArray = new char[fromArray.length];
        int j = 0;
        for (int i = 0; i < fromArray.length; i++) {
            if (fromArray[i] == '_') {
                // 当前字符为下划线时，将指针后移一位，将紧随下划线后面一个字符转成大写并存放
                i++;
                if (i < fromArray.length) {
                    toArray[j++] = Character.toUpperCase(fromArray[i]);
                }
            } else {
                toArray[j++] = fromArray[i];
            }
        }
        return new String(toArray, 0, j);
    }

    /**
     * 将字符串数据组拼接一个字符串
     *
     * @param stringArray
     *
     * @return
     */
    public static String join(String[] stringArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            sb.append(stringArray[i]);
        }
        return sb.toString();
    }

    /**
     * 将字符串数组拼接一个字符串，拼接时数组每个对象都会间隔拼接separator字符串
     *
     * @param stringArray
     * @param separator
     *
     * @return
     */
    public static String join(String[] stringArray, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(stringArray[i]);
        }
        return sb.toString();
    }

}