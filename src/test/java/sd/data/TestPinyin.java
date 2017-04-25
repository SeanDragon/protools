package sd.data;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import pro.tools.data.text.ToolPinYin4J;

import java.util.Arrays;

/**
 * @author SeanDragon
 *         Create By 2017-04-25 10:27
 */
public class TestPinyin {
    @Test
    public void test1() throws BadHanyuPinyinOutputFormatCombination {
        String src = "你好,世界!";
        String pingYin = ToolPinYin4J.getPinYin(src);
        System.out.println(pingYin);
        String pinYinHeadChar = ToolPinYin4J.getPinYinHeadChar(src);
        System.out.println(pinYinHeadChar);
        String cnASCII = ToolPinYin4J.getCnASCII(src);
        System.out.println(Arrays.toString(new String[]{Hex.toHexString(cnASCII.getBytes())}));
    }
}
