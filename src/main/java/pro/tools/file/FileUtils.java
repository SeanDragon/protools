package pro.tools.file;

import pro.tools.constant.StrConst;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static pro.tools.file.FileValidUtils.isFile;
import static pro.tools.file.FileValidUtils.isFileExists;
import static pro.tools.file.FileValidUtils.readable;
import static pro.tools.file.ToolFile.writeFileFromIS;

/**
 * Created on 17/4/9 12:01 星期日.
 *
 * @author SeanDragon
 */
public final class FileUtils {
    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Path getFileByPath(String filePath) {
        return Paths.get(filePath);
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static Path createOrExistsDir(String filePath) throws IOException {
        if (isFileExists(filePath)) return Paths.get(filePath);
        return Files.createDirectories(Paths.get(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Path createOrExistsFile(String filePath) throws IOException {
        if (isFileExists(filePath)) return Paths.get(filePath);
        return Files.createFile(Paths.get(filePath));
    }


    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static Path createFileByDeleteOldFile(String filePath) throws IOException {
        // 文件存在并且删除失败返回false
        if (isFileExists(filePath) && isFile(filePath) && !delete(filePath)) return null;

        // 创建目录失败返回false
        if (null != createOrExistsDir(filePath)) return null;

        return Files.createFile(Paths.get(filePath));
    }

    /**
     * 复制或移动目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @param isMove      是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    //private static boolean copyOrMoveDir(String srcDirPath, String destDirPath, boolean isMove) throws IOException {
    //    // 如果目标目录在源目录中则返回false，看不懂的话好好想想递归怎么结束
    //    // 为防止以上这种情况出现出现误判，须分别在后面加个路径分隔符
    //    String srcPath = srcDirPath + File.separator;
    //    String destPath = destDirPath + File.separator;
    //    if (destPath.contains(srcPath)) return false;
    //    // 源文件不存在或者不是目录则返回false
    //    if (!isFileExists(srcPath) || !isDir(srcPath)) return false;
    //    // 目标目录不存在返回false
    //    if (null != createOrExistsDir(destPath)) return false;
    //
    //    Stream<Path> fileList = getFileList(srcPath);
    //    fileList.forEach(file -> {
    //        String fileName = file.getFileName().getFileName().toString();
    //        String oneDestFileName = destPath + fileName;
    //        if (isFile(file.toAbsolutePath().toString())) {
    //            // 如果操作失败返回false
    //            try {
    //                //if (!copyOrMoveFile(fileName, oneDestFileName, isMove)) return false;
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        } else if (isDir(fileName)) {
    //            // 如果操作失败返回false
    //            try {
    //                //if (!copyOrMoveDir(fileName, oneDestFileName, isMove)) return false;
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //
    //    });
    //    return !isMove || delete(srcPath);
    //}

    /**
     * 复制或移动文件
     *
     * @param srcFileName  源文件
     * @param destFileName 目标文件
     * @param isMove       是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    private static boolean copyOrMoveFile(String srcFileName, String destFileName, boolean isMove) throws IOException {
        // 源文件不存在或者不是文件则返回false
        if (isFile(srcFileName)) return false;
        // 目标文件存在且是文件则返回false
        if (isFile(destFileName)) return false;
        // 目标目录不存在返回false
        if (null != createOrExistsDir(destFileName)) return false;
        return writeFileFromIS(destFileName, new FileInputStream(srcFileName), false)
                && !(isMove && !delete(srcFileName));
    }

    /**
     * 将文件移动到新的路径中
     * @param filePath 源文件路径
     * @param newName 新目录
     * @return
     * @throws IOException
     */
    public static Path rename(String filePath, String newName) throws IOException {
        if (!isFileExists(filePath)) return null;

        Path sourcePath = Paths.get(filePath);
        String sourcePathFileName = sourcePath.getFileName().toString();
        String targetFilePath = filePath.substring(0, filePath.indexOf(sourcePathFileName)) + newName;

        return Files.move(sourcePath, Paths.get(targetFilePath));
    }

    /**
     * 删除
     * @param filePath
     * @return
     * @throws IOException
     */
    public static boolean delete(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.deleteIfExists(path);
    }

    /**
     * 获得文件路径下的所有文件路径
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static Stream<Path> getFileList(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.list(path);
    }

    /**
     * 以UTF-8格式获得文件路径下的所有文件路径
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Stream<String> readFile2List(String filePath) throws IOException {
        return readFile2List(filePath, StrConst.DEFAULT_CHARSET);
    }

    /**
     * 以指定字符格式获得文件路径下的所有文件路径
     * @param filePath 文件路径
     * @param charset 字符格式
     * @return
     * @throws IOException
     */
    public static Stream<String> readFile2List(String filePath, Charset charset) throws IOException {
        if (!readable(filePath)) {
            return null;
        }
        Path path = Paths.get(filePath);
        return Files.lines(path, charset);
    }
}
