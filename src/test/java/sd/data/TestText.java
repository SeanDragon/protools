package sd.data;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;
import pro.tools.data.text.ToolPinYin4J;

/**
 * @author SeanDragon
 *         Create By 2017-04-10 10:29
 */
public class TestText {
    @Test
    public void test1() throws BadHanyuPinyinOutputFormatCombination {
        String cnStr = "";
        System.out.println(ToolPinYin4J.getPingYin(cnStr));
        System.out.println(ToolPinYin4J.getPinYinHeadChar(cnStr));
        System.out.println(ToolPinYin4J.getCnASCII(cnStr));
    }
}
