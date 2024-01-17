package pro.tools.file;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import com.google.common.collect.Lists;
import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolStr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static pro.tools.constant.UnitConst.KB;


/**
 * 压缩相关工具
 *
 * @author SeanDragon
 */
public final class ToolZip {

    private ToolZip() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 批量压缩文件
     *
     * @param resFiles
     *         待压缩文件集合
     * @param zipFilePath
     *         压缩文件路径
     *
     * @return {@code true}: 压缩成功<br>{@code false}: 压缩失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean zipFiles(Collection<File> resFiles, String zipFilePath)
            throws IOException {
        return zipFiles(resFiles, zipFilePath, null);
    }

    /**
     * 批量压缩文件
     *
     * @param resFiles
     *         待压缩文件集合
     * @param zipFilePath
     *         压缩文件路径
     * @param comment
     *         压缩文件的注释
     *
     * @return {@code true}: 压缩成功<br>{@code false}: 压缩失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean zipFiles(Collection<File> resFiles, String zipFilePath, String comment)
            throws IOException {
        return zipFiles(resFiles, ToolFile.getFileByPath(zipFilePath), comment);
    }

    /**
     * 批量压缩文件
     *
     * @param resFiles
     *         待压缩文件集合
     * @param zipFile
     *         压缩文件
     *
     * @return {@code true}: 压缩成功<br>{@code false}: 压缩失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean zipFiles(Collection<File> resFiles, File zipFile)
            throws IOException {
        return zipFiles(resFiles, zipFile, null);
    }

    /**
     * 批量压缩文件
     *
     * @param resFiles
     *         待压缩文件集合
     * @param zipFile
     *         压缩文件
     * @param comment
     *         压缩文件的注释
     *
     * @return {@code true}: 压缩成功<br>{@code false}: 压缩失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean zipFiles(Collection<File> resFiles, File zipFile, String comment)
            throws IOException {
        if (resFiles == null || zipFile == null) {
            return false;
        }

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File resFile : resFiles) {
                if (!zipFile(resFile, "", zos, comment)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 压缩文件
     *
     * @param resFilePath
     *         待压缩文件路径
     * @param zipFilePath
     *         压缩文件路径
     *
     * @return {@code true}: 压缩成功<br>{@code false}: 压缩失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean zipFile(String resFilePath, String zipFilePath)
            throws IOException {
        return zipFile(resFilePath, zipFilePath, null);
    }

    /**
     * 压缩文件
     *
     * @param resFilePath
     *         待压缩文件路径
     * @param zipFilePath
     *         压缩文件路径
     * @param comment
     *         压缩文件的注释
     *
     * @return {@code true}: 压缩成功<br>{@code false}: 压缩失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean zipFile(String resFilePath, String zipFilePath, String comment)
            throws IOException {
        return zipFile(ToolFile.getFileByPath(resFilePath), ToolFile.getFileByPath(zipFilePath), comment);
    }

    /**
     * 压缩文件
     *
     * @param resFile
     *         待压缩文件
     * @param zipFile
     *         压缩文件
     *
     * @return {@code true}: 压缩成功<br>{@code false}: 压缩失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean zipFile(File resFile, File zipFile)
            throws IOException {
        return zipFile(resFile, zipFile, null);
    }

    /**
     * 压缩文件
     *
     * @param resFile
     *         待压缩文件
     * @param zipFile
     *         压缩文件
     * @param comment
     *         压缩文件的注释
     *
     * @return {@code true}: 压缩成功<br>{@code false}: 压缩失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean zipFile(File resFile, File zipFile, String comment)
            throws IOException {
        if (resFile == null || zipFile == null) {
            return false;
        }

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            return zipFile(resFile, "", zos, comment);
        }
    }

    /**
     * 压缩文件
     *
     * @param resFile
     *         待压缩文件
     * @param rootPath
     *         相对于压缩文件的路径
     * @param zos
     *         压缩文件输出流
     * @param comment
     *         压缩文件的注释
     *
     * @return {@code true}: 压缩成功<br>{@code false}: 压缩失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    private static boolean zipFile(File resFile, String rootPath, ZipOutputStream zos, String comment)
            throws IOException {
        rootPath = rootPath + (ToolStr.isSpace(rootPath) ? "" : StrConst.FILE_SEP) + resFile.getName();
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            // 如果是空文件夹那么创建它，我把'/'换为StrConst.FILE_SEP测试就不成功，eggPain
            if (fileList == null || fileList.length <= 0) {
                ZipEntry entry = new ZipEntry(rootPath + '/');
                if (!ToolStr.isEmpty(comment)) {
                    entry.setComment(comment);
                }
                zos.putNextEntry(entry);
                zos.closeEntry();
            } else {
                for (File file : fileList) {
                    // 如果递归返回false则返回false
                    if (!zipFile(file, rootPath, zos, comment)) {
                        return false;
                    }
                }
            }
        } else {
            try (InputStream is = new BufferedInputStream(new FileInputStream(resFile))) {
                ZipEntry entry = new ZipEntry(rootPath);
                if (!ToolStr.isEmpty(comment)) {
                    entry.setComment(comment);
                }
                zos.putNextEntry(entry);
                byte[] buffer = new byte[KB];
                int len;
                while ((len = is.read(buffer, 0, KB)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            }
        }
        return true;
    }

    /**
     * 批量解压文件
     *
     * @param zipFiles
     *         压缩文件集合
     * @param destDirPath
     *         目标目录路径
     *
     * @return {@code true}: 解压成功<br>{@code false}: 解压失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean unzipFiles(Collection<File> zipFiles, String destDirPath)
            throws IOException {
        return unzipFiles(zipFiles, ToolFile.getFileByPath(destDirPath));
    }

    /**
     * 批量解压文件
     *
     * @param zipFiles
     *         压缩文件集合
     * @param destDir
     *         目标目录
     *
     * @return {@code true}: 解压成功<br>{@code false}: 解压失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean unzipFiles(Collection<File> zipFiles, File destDir)
            throws IOException {
        if (zipFiles == null || destDir == null) {
            return false;
        }
        for (File zipFile : zipFiles) {
            if (!unzipFile(zipFile, destDir)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 解压文件
     *
     * @param zipFilePath
     *         待解压文件路径
     * @param destDirPath
     *         目标目录路径
     *
     * @return {@code true}: 解压成功<br>{@code false}: 解压失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean unzipFile(String zipFilePath, String destDirPath)
            throws IOException {
        return unzipFile(ToolFile.getFileByPath(zipFilePath), ToolFile.getFileByPath(destDirPath));
    }

    /**
     * 解压文件
     *
     * @param zipFile
     *         待解压文件
     * @param destDir
     *         目标目录
     *
     * @return {@code true}: 解压成功<br>{@code false}: 解压失败
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static boolean unzipFile(File zipFile, File destDir)
            throws IOException {
        return unzipFileByKeyword(zipFile, destDir, null) != null;
    }

    /**
     * 解压带有关键字的文件
     *
     * @param zipFilePath
     *         待解压文件路径
     * @param destDirPath
     *         目标目录路径
     * @param keyword
     *         关键字
     *
     * @return 返回带有关键字的文件链表
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static List<File> unzipFileByKeyword(String zipFilePath, String destDirPath, String keyword)
            throws IOException {
        return unzipFileByKeyword(ToolFile.getFileByPath(zipFilePath),
                ToolFile.getFileByPath(destDirPath), keyword);
    }

    /**
     * 解压带有关键字的文件
     *
     * @param zipFile
     *         待解压文件
     * @param destDir
     *         目标目录
     * @param keyword
     *         关键字
     *
     * @return 返回带有关键字的文件链表
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static List<File> unzipFileByKeyword(File zipFile, File destDir, String keyword)
            throws IOException {
        if (zipFile == null || destDir == null) {
            return null;
        }
        List<File> files = Lists.newArrayList();
        try (ZipFile zf = new ZipFile(zipFile)) {
            Enumeration<?> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (ToolStr.isEmpty(keyword) || ToolFile.getFileName(entryName).toLowerCase().contains(keyword.toLowerCase())) {
                    String filePath = destDir + StrConst.FILE_SEP + entryName;
                    File file = new File(filePath);
                    files.add(file);
                    if (entry.isDirectory()) {
                        if (!ToolFile.createOrExistsDir(file)) {
                            return null;
                        }
                    } else {
                        if (!ToolFile.createOrExistsFile(file)) {
                            return null;
                        }
                        try (InputStream in = new BufferedInputStream(zf.getInputStream(entry));
                             OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                            byte[] buffer = new byte[KB];
                            int len;
                            while ((len = in.read(buffer)) != -1) {
                                out.write(buffer, 0, len);
                            }
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            if ("MALFORMED".equals(e.getMessage())) {
                return unzipFileByKeyword(zipFile, destDir, keyword, Charset.forName("GBK"));
            } else {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return files;
    }

    /**
     * 解压带有关键字的文件
     *
     * @param zipFile
     *         待解压文件
     * @param destDir
     *         目标目录
     * @param keyword
     *         关键字
     *
     * @return 返回带有关键字的文件链表
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static List<File> unzipFileByKeyword(File zipFile, File destDir, String keyword, Charset charset)
            throws IOException {
        if (zipFile == null || destDir == null) {
            return null;
        }
        List<File> files = Lists.newArrayList();
        try (ZipFile zf = new ZipFile(zipFile, charset)) {
            Enumeration<?> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (ToolStr.isEmpty(keyword) || ToolFile.getFileName(entryName).toLowerCase().contains(keyword.toLowerCase())) {
                    String filePath = destDir + StrConst.FILE_SEP + entryName;
                    File file = new File(filePath);
                    files.add(file);
                    if (entry.isDirectory()) {
                        if (!ToolFile.createOrExistsDir(file)) {
                            return null;
                        }
                    } else {
                        if (!ToolFile.createOrExistsFile(file)) {
                            return null;
                        }
                        try (InputStream in = new BufferedInputStream(zf.getInputStream(entry));
                             OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                            byte[] buffer = new byte[KB];
                            int len;
                            while ((len = in.read(buffer)) != -1) {
                                out.write(buffer, 0, len);
                            }
                        }
                    }
                }
            }
        }
        return files;
    }

    /**
     * 解压rar
     *
     * @param rarFiles
     *         带解压压缩包列表
     * @param destDirPath
     *         目标目录
     *
     * @return 成功与否
     *
     * @throws IOException
     *         IO错误抛出
     * @throws RarException
     *         IO错误抛出
     */
    public static boolean unrarFiles(Collection<File> rarFiles, String destDirPath) throws IOException, RarException {
        return unrarFiles(rarFiles, ToolFile.getFileByPath(destDirPath));
    }

    /**
     * 解压rar
     *
     * @param rarFiles
     *         带解压压缩包列表
     * @param destDir
     *         目标目录
     *
     * @return 成功与否
     *
     * @throws IOException
     *         IO错误抛出
     * @throws RarException
     *         IO错误抛出
     */
    public static boolean unrarFiles(Collection<File> rarFiles, File destDir) throws IOException, RarException {
        if (rarFiles == null || destDir == null) {
            return false;
        }
        for (File rarFile : rarFiles) {
            if (!unrarFile(rarFile, destDir)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 解压rar
     *
     * @param rarFilePath
     *         带解压压缩包列表
     * @param destDirPath
     *         目标目录
     *
     * @return 成功与否
     *
     * @throws IOException
     *         IO错误抛出
     * @throws RarException
     *         IO错误抛出
     */
    public static boolean unrarFile(String rarFilePath, String destDirPath) throws IOException, RarException {
        return unrarFile(ToolFile.getFileByPath(rarFilePath), ToolFile.getFileByPath(destDirPath));
    }

    /**
     * 解压rar
     *
     * @param rarFile
     *         带解压压缩包列表
     * @param destDir
     *         目标目录
     *
     * @return 成功与否
     *
     * @throws IOException
     *         IO错误抛出
     * @throws RarException
     *         IO错误抛出
     */
    public static boolean unrarFile(File rarFile, File destDir) throws IOException, RarException {
        try (Archive archive = new Archive(rarFile)) {
            FileHeader fh = archive.nextFileHeader();
            while (fh != null) {
                String compressFileName = fh.getFileNameString().trim();
                File destFile = new File(destDir.getAbsolutePath() + StrConst.FILE_SEP + compressFileName);

                if (fh.isDirectory()) {
                    if (!destFile.exists()) {
                        destFile.mkdirs();
                    }
                    fh = archive.nextFileHeader();
                    continue;
                }

                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }

                try (FileOutputStream fos = new FileOutputStream(destFile)) {
                    archive.extractFile(fh, fos);
                }
                fh = archive.nextFileHeader();
            }
        }
        return true;
    }


    /**
     * 获取压缩文件中的文件路径链表
     *
     * @param zipFilePath
     *         压缩文件路径
     *
     * @return 压缩文件中的文件路径链表
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static List<String> getFilesPath(String zipFilePath)
            throws IOException {
        return getFilesPath(ToolFile.getFileByPath(zipFilePath));
    }

    /**
     * 获取压缩文件中的文件路径链表
     *
     * @param zipFile
     *         压缩文件
     *
     * @return 压缩文件中的文件路径链表
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static List<String> getFilesPath(File zipFile)
            throws IOException {
        if (zipFile == null) {
            return null;
        }
        List<String> paths = Lists.newArrayList();
        Enumeration<?> entries = getEntries(zipFile);
        while (entries.hasMoreElements()) {
            paths.add(((ZipEntry) entries.nextElement()).getName());
        }
        return paths;
    }

    /**
     * 获取压缩文件中的注释链表
     *
     * @param zipFilePath
     *         压缩文件路径
     *
     * @return 压缩文件中的注释链表
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static List<String> getComments(String zipFilePath)
            throws IOException {
        return getComments(ToolFile.getFileByPath(zipFilePath));
    }

    /**
     * 获取压缩文件中的注释链表
     *
     * @param zipFile
     *         压缩文件
     *
     * @return 压缩文件中的注释链表
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static List<String> getComments(File zipFile)
            throws IOException {
        if (zipFile == null) {
            return null;
        }
        List<String> comments = Lists.newArrayList();
        Enumeration<?> entries = getEntries(zipFile);
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            comments.add(entry.getComment());
        }
        return comments;
    }

    /**
     * 获取压缩文件中的文件对象
     *
     * @param zipFilePath
     *         压缩文件路径
     *
     * @return 压缩文件中的文件对象
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static Enumeration<?> getEntries(String zipFilePath)
            throws IOException {
        return getEntries(ToolFile.getFileByPath(zipFilePath));
    }

    /**
     * 获取压缩文件中的文件对象
     *
     * @param file
     *         压缩文件
     *
     * @return 压缩文件中的文件对象
     *
     * @throws IOException
     *         IO错误时抛出
     */
    public static Enumeration<?> getEntries(File file)
            throws IOException {
        if (file == null) {
            return null;
        }
        Enumeration<? extends ZipEntry> entries;
        try (ZipFile zipFile = new ZipFile(file)) {
            entries = zipFile.entries();
        }
        return entries;
    }
}