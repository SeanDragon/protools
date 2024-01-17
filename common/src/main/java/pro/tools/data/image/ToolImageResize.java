package pro.tools.data.image;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图像压缩工具
 *
 * @author SeanDragon
 */
@SuppressWarnings("restriction")
public final class ToolImageResize {

    private ToolImageResize() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    public static final MediaTracker TRACKER = new MediaTracker(new Component() {
        private static final long serialVersionUID = 1234162663955668507L;
    });

    /**
     * @param oldPath
     *         原图像
     * @param newPath
     *         压缩后的图像
     * @param width
     *         图像宽
     * @param format
     *         图片格式 jpg, jpeg, png, gif(非动画)
     */
    public static void resize(String oldPath, String newPath, int width, String format) throws IOException {
        File originalFile = new File(oldPath);
        File resizedFile = new File(newPath);
        if (format != null && "gif".equals(format.toLowerCase())) {
            resize(originalFile, resizedFile, width, 1);
            return;
        }
        byte[] in;
        try (FileInputStream fis = new FileInputStream(originalFile);
             ByteArrayOutputStream byteStream = new ByteArrayOutputStream()) {
            int readLength;
            int bufferSize = 1024;
            byte[] bytes = new byte[bufferSize];
            while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
                byteStream.write(bytes, 0, readLength);
            }
            in = byteStream.toByteArray();

            Image inputImage = Toolkit.getDefaultToolkit().createImage(in);
            waitForImage(inputImage);
            int imageWidth = inputImage.getWidth(null);
            if (imageWidth < 1) {
                throw new IllegalArgumentException("image width " + imageWidth + " is out of range");
            }
            int imageHeight = inputImage.getHeight(null);
            if (imageHeight < 1) {
                throw new IllegalArgumentException("image height " + imageHeight + " is out of range");
            }

            // Create output image.
            int height = -1;
            double scaleW = (double) imageWidth / (double) width;
            double scaleY = (double) imageHeight / (double) height;
            if (scaleW >= 0 && scaleY >= 0) {
                if (scaleW > scaleY) {
                    height = -1;
                } else {
                    width = -1;
                }
            }
            Image outputImage = inputImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            checkImage(outputImage);
            encode(new FileOutputStream(resizedFile), outputImage, format);
        }
    }

    /**
     * 检验图片的宽和高
     *
     * @param image
     *         图片对象
     */
    private static void checkImage(Image image) {
        waitForImage(image);
        int imageWidth = image.getWidth(null);
        if (imageWidth < 1) {
            throw new IllegalArgumentException("image width " + imageWidth + " is out of range");
        }
        int imageHeight = image.getHeight(null);
        if (imageHeight < 1) {
            throw new IllegalArgumentException("image height " + imageHeight + " is out of range");
        }
    }

    /**
     * 图片处理队列,手动放入等待
     *
     * @param image
     *         图片
     */
    private static void waitForImage(Image image) {
        try {
            TRACKER.addImage(image, 0);
            TRACKER.waitForID(0);
            TRACKER.removeImage(image, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将图片写入到输出流中
     */
    private static void encode(OutputStream outputStream, Image outputImage, String format) throws IOException {
        int outputWidth = outputImage.getWidth(null);
        if (outputWidth < 1) {
            throw new IllegalArgumentException("output image width " + outputWidth + " is out of range");
        }
        int outputHeight = outputImage.getHeight(null);
        if (outputHeight < 1) {
            throw new IllegalArgumentException("output image height " + outputHeight + " is out of range");
        }

        // Get a buffered image from the image.
        BufferedImage bi = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D biContext = bi.createGraphics();
        biContext.drawImage(outputImage, 0, 0, null);
        ImageIO.write(bi, format, outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 缩放gif图片
     *
     * @param originalFile
     *         原图片
     * @param resizedFile
     *         缩放后的图片
     * @param newWidth
     *         宽度
     * @param quality
     *         缩放比例 (等比例)
     *
     * @throws IOException
     */
    private static void resize(File originalFile, File resizedFile, int newWidth, float quality) throws IOException {
        if (quality < 0 || quality > 1) {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        }
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());

        Image i = ii.getImage();
        Image resizedImage = null;
        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);
        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
        }
        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();
        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();
        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();
        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);
        // Write the jpeg to a file.
        try (
                FileOutputStream out = new FileOutputStream(resizedFile)) {

            ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            imageWriter.setOutput(ios);
            //and metadata
            IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(bufferedImage), null);

            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(quality);
            imageWriter.write(imageMetaData, new IIOImage(bufferedImage, null, null), jpegParams);
        }
    }
}
