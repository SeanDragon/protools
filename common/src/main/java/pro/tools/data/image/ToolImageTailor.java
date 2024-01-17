package pro.tools.data.image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * 图片裁剪
 *
 * @author SeanDragon
 */
public final class ToolImageTailor {

    private ToolImageTailor() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 裁剪
     *
     * @param srcPath
     *         源图片路径名称如:c:/1.jpg
     * @param savePath
     *         剪切图片存放路径名称.如:c:/2.jpg
     * @param x
     *         剪切点x坐标
     * @param y
     * @param width
     *         剪切点大小
     * @param height
     *
     * @throws IOException
     */
    public static void cut(String srcPath, String savePath, int x, int y, int width, int height) throws IOException {
        String endWiths = srcPath.substring(srcPath.lastIndexOf(".") + 1);// 后缀

        /*
         * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
         */
        Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = it.next();


        try (
                // 读取图片文件
                FileInputStream is = new FileInputStream(srcPath);

                // 获取图片流
                ImageInputStream iis = ImageIO.createImageInputStream(is)
        ) {
            /*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis, true);

            /*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回
             * ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();

            /*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
             */
            Rectangle rect = new Rectangle(x, y, width, height);

            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);

            /*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的 BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0, param);

            // 保存新图片
            ImageIO.write(bi, endWiths, new File(savePath)); // "jpg"
        }
    }
}
