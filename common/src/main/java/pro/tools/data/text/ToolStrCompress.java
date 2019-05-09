package pro.tools.data.text;

import pro.tools.constant.StrConst;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 对字符串的压缩及解压
 *
 * @author SeanDragon
 */
public class ToolStrCompress {

    private ToolStrCompress() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 字符串的压缩
     *
     * @param str 待压缩的字符串
     *
     * @return 返回压缩后的字符串
     *
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        try (
                // 创建一个新的 byte 数组输出流
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                // 使用默认缓冲区大小创建新的输出流
                GZIPOutputStream gzip = new GZIPOutputStream(out);) {
            // 将 b.length 个字节写入此输出流
            gzip.write(str.getBytes());
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toString(StrConst.DEFAULT_CHARSET_NAME);
        }
    }

    /**
     * 字符串的解压
     *
     * @param str 对字符串解压
     *
     * @return 返回解压缩后的字符串
     *
     * @throws IOException
     */
    public static String unCompress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        try (
                // 创建一个新的 byte 数组输出流
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
                ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes(StrConst.DEFAULT_CHARSET_NAME));
                // 使用默认缓冲区大小创建新的输入流
                GZIPInputStream gzip = new GZIPInputStream(in);) {
            byte[] buffer = new byte[256];
            int n = 0;
            // 将未压缩数据读入字节数组
            while ((n = gzip.read(buffer)) >= 0) {
                // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
                out.write(buffer, 0, n);
            }
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toString(StrConst.DEFAULT_CHARSET_NAME);
        }
    }

}
