package pro.tools.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created on 17/4/9 12:14 星期日.
 *
 * @author SeanDragon
 */
public final class ToolFileValid {
    private ToolFileValid() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 是否是一个文件路径
     * @param filePath 文件路径
     * @return
     */
    public static boolean isFile(String filePath) {
        if (!isFileExists(filePath)) return false;
        Path path = Paths.get(filePath);
        return Files.isRegularFile(path);
    }

    /**
     * 是否是一个目录
     * @param filePath 文件夹路径
     * @return
     */
    public static boolean isDir(String filePath) {
        if (!isFileExists(filePath)) return false;
        Path path = Paths.get(filePath);
        return Files.isDirectory(path);
    }

    /**
     * 不是一个文件夹且一定是一个文件
     * @param filePath 文件路径
     * @return
     */
    public static boolean readable(String filePath) {
        if (!isFileExists(filePath)) return false;
        Path path = Paths.get(filePath);
        boolean isFile = !Files.isDirectory(path);
        boolean readable = Files.isReadable(path);
        return isFile && readable;
    }


    /**
     * 文件或目录是否存在
     * @param filePath 文件或目录路径
     * @return
     */
    public static boolean isFileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
}
