package pro.tools.data.image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import pro.tools.constant.StrConst;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Hashtable;

/**
 * 条码处理
 *
 * @author SeanDragon
 */
public final class ToolBarCode {

    private ToolBarCode() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 生成二维码
     *
     * @param content
     *         条码文本内容
     * @param width
     *         条码宽度
     * @param height
     *         条码高度
     * @param fileType
     *         文件类型，如png
     * @param savePath
     *         保存路径
     */
    @SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
    @Deprecated
    public static void encode(String content, int width, int height, String fileType, String savePath) throws IOException, WriterException {
        content = new String(content.getBytes(StrConst.DEFAULT_CHARSET_NAME), StrConst.DEFAULT_CHARSET_NAME);// 二维码内容
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, StrConst.DEFAULT_CHARSET_NAME);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        File file = new File(savePath);
        MatrixToImageWriter.writeToPath(bitMatrix, fileType, file.toPath());
    }

    /**
     * 生成带logo的二维码
     *
     * @param content
     *         条码文本内容
     * @param width
     *         条码宽度
     * @param height
     *         条码高度
     * @param logoPath
     *         条码中logo的路径
     * @param fileType
     *         文件类型，如png
     * @param savePath
     *         保存路径
     */
    public static void encodeLogo(String content, int width, int height, String logoPath, String fileType, String savePath) throws IOException {
        BitMatrix matrix = MatrixToImageWriterEx.createQRCode(content, width, height);
        MatrixToLogoImageConfig logoConfig = new MatrixToLogoImageConfig(Color.BLUE, 4);
        MatrixToImageWriterEx.writeToFile(matrix, fileType, savePath, logoPath, logoConfig);
    }

    /**
     * 解码
     *
     * @param filePath
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String decode(String filePath) throws IOException, NotFoundException {
        BufferedImage image;
        image = ImageIO.read(new File(filePath));
        if (image == null) {
            return "Could not decode image";
        }
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, StrConst.DEFAULT_CHARSET_NAME);
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

}

/**
 * 定制logo属性类
 */
class MatrixToLogoImageConfig {

    // logo默认边框颜色
    /**
     * logo默认边框颜色
     */
    public static final Color DEFAULT_BORDER_COLOR = Color.RED;

    // logo默认边框宽度
    /**
     * logo默认边框宽度
     */
    public static final int DEFAULT_BORDER = 2;

    // logo大小默认为照片的1/5
    /**
     * logo大小默认为照片的1/5
     */
    public static final int DEFAULT_LOGO_PART = 5;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    public MatrixToLogoImageConfig() {
        this(DEFAULT_BORDER_COLOR, DEFAULT_LOGO_PART);
    }

    public MatrixToLogoImageConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }
}

/**
 * 生成二维码logo扩展类，此类是在zxing的基础上进行扩展的， 用于在二维码上定制自己的logo
 */
class MatrixToImageWriterEx {

    private static final MatrixToLogoImageConfig DEFAULT_CONFIG = new MatrixToLogoImageConfig();

    /**
     * 根据内容生成二维码数据
     *
     * @param content
     *         二维码文字内容[为了信息安全性，一般都要先进行数据加密]
     * @param width
     *         二维码照片宽度
     * @param height
     *         二维码照片高度
     *
     * @return
     */
    public static BitMatrix createQRCode(String content, int width, int height) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 设置字符编码
        hints.put(EncodeHintType.CHARACTER_SET, StrConst.DEFAULT_CHARSET_NAME);
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    /**
     * 写入二维码、以及将照片logo写入二维码中
     *
     * @param matrix
     *         要写入的二维码
     * @param format
     *         二维码照片格式
     * @param imagePath
     *         二维码照片保存路径
     * @param logoPath
     *         logo路径
     *
     * @throws IOException
     */
    public static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath) throws IOException {
        MatrixToImageWriter.writeToPath(matrix, format, Paths.get(imagePath), new MatrixToImageConfig());

        // 添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
        BufferedImage img = ImageIO.read(new File(imagePath));
        MatrixToImageWriterEx.overlapImage(img, format, imagePath, logoPath, DEFAULT_CONFIG);
    }

    /**
     * 写入二维码、以及将照片logo写入二维码中
     *
     * @param matrix
     *         要写入的二维码
     * @param format
     *         二维码照片格式
     * @param imagePath
     *         二维码照片保存路径
     * @param logoPath
     *         logo路径
     * @param logoConfig
     *         logo配置对象
     *
     * @throws IOException
     */
    public static void writeToFile(BitMatrix matrix, String format, String imagePath, String logoPath, MatrixToLogoImageConfig logoConfig) throws IOException {
        MatrixToImageWriter.writeToPath(matrix, format, Paths.get(imagePath), new MatrixToImageConfig());

        // 添加logo图片, 此处一定需要重新进行读取，而不能直接使用二维码的BufferedImage 对象
        BufferedImage img = ImageIO.read(new File(imagePath));
        MatrixToImageWriterEx.overlapImage(img, format, imagePath, logoPath, logoConfig);
    }

    /**
     * 将照片logo添加到二维码中间
     *
     * @param image
     *         生成的二维码照片对象
     * @param imagePath
     *         照片保存路径
     * @param logoPath
     *         logo照片路径
     * @param formate
     *         照片格式
     */
    public static void overlapImage(BufferedImage image, String formate, String imagePath, String logoPath, MatrixToLogoImageConfig logoConfig) throws IOException {
        BufferedImage logo = ImageIO.read(new File(logoPath));
        Graphics2D g = image.createGraphics();
        // 考虑到logo照片贴到二维码中，建议大小不要超过二维码的1/5;
        int width = image.getWidth() / logoConfig.getLogoPart();
        int height = image.getHeight() / logoConfig.getLogoPart();
        // logo起始位置，此目的是为logo居中显示
        int x = (image.getWidth() - width) / 2;
        int y = (image.getHeight() - height) / 2;
        // 绘制图
        g.drawImage(logo, x, y, width, height, null);

        // 给logo画边框
        // 构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
        g.setStroke(new BasicStroke(logoConfig.getBorder()));
        g.setColor(logoConfig.getBorderColor());
        g.drawRect(x, y, width, height);

        g.dispose();
        // 写入logo照片到二维码
        ImageIO.write(image, formate, new File(imagePath));
    }

}
