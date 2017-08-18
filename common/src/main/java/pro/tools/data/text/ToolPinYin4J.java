package pro.tools.data.text;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import pro.tools.constant.StrConst;

/**
 * 汉子拼音转换类
 *
 * @author SeanDragon
 */
public final class ToolPinYin4J {

    private ToolPinYin4J() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 得到 全拼
     *
     * @param src
     * @return
     */
    public static String getPinYin(String src) throws BadHanyuPinyinOutputFormatCombination {
        char[] t1;
        t1 = src.toCharArray();
        String[] t2;
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder t4 = new StringBuilder();
        for (char aT1 : t1) {
            // 判断是否为汉字字符
            if (Character.toString(aT1).matches("[\\u4E00-\\u9FA5]+")) {
                t2 = PinyinHelper.toHanyuPinyinStringArray(aT1, t3);
                t4.append(t2[0]);
            } else {
                t4.append(Character.toString(aT1));
            }
        }
        return t4.toString();
    }

    /**
     * 得到中文首字母
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {

        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString();
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuilder sb = new StringBuilder();
        byte[] strByte = cnStr.getBytes(StrConst.DEFAULT_CHARSET);
        for (byte aStrByte : strByte) {
            sb.append(Integer.toHexString(aStrByte & 0xff));
        }
        return sb.toString();
    }

}
