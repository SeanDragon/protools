package sd.http.once;

import org.junit.Test;
import pro.tools.http.jdk.ToolHttp;
import pro.tools.http.pojo.HttpReceive;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-27 15:21
 */
public class TestBUG {
    @Test
    public void test1() {
        String url = " http://192.168.15.20:84/manage/updateThirdApiStatus/0B0D0C070939141E030B1D04311215041C4B5B-ewogICJpZCI6ICJnYWJTa2ZoZzR5RjhTYUt2MGJ5QjhsIiwKICAibmFtZSI6ICIxMTEiLAogICJuaWQiOiAiMTExIiwKICAidmFsdWUiOiAiMTExMjIyMTEiLAogICJzdGF0dXMiOiB0cnVlLAogICJ1c2VkIjogMCwKICAicmVtYXJrIjogIjExMTFmZmZmZmZmICIsCiAgImFkZFRpbWUiOiAiMjAxNy0wNy0yNyAxNDo0Mjo0NzowMDAiCn0=";

        url = " http://192.168.15.20:84/manage/updateThirdApiStatus/0B0D0C070939141E030B1D04311215041C4B5B-ewogICJpZCI6ICJnYWJTa2ZoZzR5RjhTYUt2MGJ5QjhsIiwKICAibmFtZSI6ICIxMTEiLAogICJuaWQiOiAiMTExIiwKICAidmFsdWUiOiAiMTExMjIyMTEiLAogICJzdGF0dXMiOiB0cnVlLAogICJ1c2VkIjogMCwKICAicmVtYXJrIjogIkFCQyIsCiAgImFkZFRpbWUiOiAiMjAxNy0wNy0yNyAxNDo0Mjo0NzowMDAiCn0=";


        //URL decode = null;
        //try {
        //    decode = new URL(url);
        //} catch (Exception e) {
        //    System.err.println("error");
        //}

        //System.out.println(decode);

        HttpReceive httpReceive = ToolHttp.sendPost(url);

        System.out.println(httpReceive);
    }
}
