package sd.data;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.junit.Test;
import pro.tools.data.image.ToolBarCode;
import pro.tools.data.image.ToolImagePressText;

import java.awt.*;
import java.io.IOException;

/**
 * @author SeanDragon
 *         Create By 2017-04-10 10:10
 */
public class TestImage {


    public void test1() throws IOException, WriterException, NotFoundException {
        String content = "http://www.hikvision.com.cn";
        int width = 200;
        int height = 200;
        String logoPath = "d:/logo.png";
        String fileType = "jpg";
        String savePath = "d:/code.jpg";

        ToolBarCode.encode(content, width, height, fileType, savePath);

        ToolBarCode.encodeLogo(content, width, height, logoPath, fileType, savePath);

        String text = ToolBarCode.decode(savePath);
        System.out.println(text);
    }

    @Test
    public void test2() throws IOException {
        //图片水印处理
        //pressImage("D:/194122749129953.jpg", "D:/aaa.jpg", 20, 20);//前面的图片印刷到后面的图片
        ToolImagePressText.pressText("http://www.4bu4.com", "d:/sago.jpg", "", 11, Color.RED, 20, 300, 20);//右下角是坐标定点
    }
}
