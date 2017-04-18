package pro.tools.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created on 17/4/9 12:14 星期日.
 *
 * @author SeanDragon
 */
public final class FileValidUtils {
    private FileValidUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isFile(String filePath) {
        if (!isFileExists(filePath)) return false;
        Path path = Paths.get(filePath);
        return Files.isRegularFile(path);
    }

    public static boolean isDir(String filePath) {
        if (!isFileExists(filePath)) return false;
        Path path = Paths.get(filePath);
        return Files.isDirectory(path);
    }

    public static boolean readable(String filePath) {
        if (!isFileExists(filePath)) return false;
        Path path = Paths.get(filePath);
        boolean isFile = !Files.isDirectory(path);
        boolean readable = Files.isReadable(path);
        return isFile && readable;
    }


    public static boolean isFileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
}
