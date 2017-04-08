package pro.tools.data.text;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.nio.charset.Charset;

/**
 * 汉子拼音转换类
 */
public class ToolPinYin4J {


    /**
     * 得到 全拼
     *
     * @param src
     * @return
     */
    public static String getPingYin(String src) {
        char[] t1;
        t1 = src.toCharArray();
        String[] t2;
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder t4 = new StringBuilder();
        try {
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
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
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

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param cnStr
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuilder sb = new StringBuilder();
        byte[] strByte = cnStr.getBytes(Charset.forName("UTF-8"));
        for (int i = 0; i < strByte.length; i++) {
            // System.out.println(Integer.toHexString(bGBK[i]&0xff));
            sb.append(Integer.toHexString(strByte[i] & 0xff));
        }
        String val = sb.toString();
        sb = null;
        return val;
    }

    public static void main(String[] args) {
        String cnStr = "";
        System.out.println(getPingYin(cnStr));
        System.out.println(getPinYinHeadChar(cnStr));
        System.out.println(getCnASCII(cnStr));
    }

}
