package pro.tools.file;

import pro.tools.data.text.ToolConvert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件类型判断类
 *
 * @author SeanDragon
 */
public final class ToolFileType {
    /**
     * Constructor
     */
    private ToolFileType() {
    }

    /**
     * 得到文件头
     *
     * @param inputStream
     *         文件路径
     *
     * @return 文件头
     *
     * @throws IOException
     */
    private static String getFileContent(InputStream inputStream) throws IOException {

        byte[] b = new byte[28];

        try {
            inputStream.read(b, 0, 28);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return ToolConvert.bytes2HexString(b);
    }

    /**
     * 判断文件类型
     *
     * @param inputStream
     *         文件流
     *
     * @return 文件类型
     */
    public static FileType getType(InputStream inputStream) throws IOException {

        String fileHead = getFileContent(inputStream);

        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }
        return null;
    }

    /**
     * 判断文件类型
     *
     * @param filePath
     *         文件路径
     *
     * @return 文件类型
     */
    public static FileType getType(String filePath) throws IOException {
        return getType(new File(filePath));
    }

    /**
     * 判断文件类型
     *
     * @param file
     *         文件
     *
     * @return 文件类型
     */
    public static FileType getType(File file) throws IOException {
        return getType(new FileInputStream(file));
    }
}