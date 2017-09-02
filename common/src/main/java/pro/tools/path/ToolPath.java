package pro.tools.path;

import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolStr;
import pro.tools.file.FileType;
import pro.tools.file.ToolFileType;
import pro.tools.path.visit.RmrVisitor;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.*;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-09-01 11:16
 */
public final class ToolPath {

    public static Path getPath(String first, String... more) {
        if (ToolStr.isBlank(first)) {
            throw new NullPointerException("first is null");
        }
        return Paths.get(first, more).normalize();
    }

    public static Path getPath(URI uri) {
        if (uri == null) {
            throw new NullPointerException("uri is null");
        }
        return Paths.get(uri).normalize();
    }

    public static boolean isExists(String path) {
        return isExists(getPath(path));
    }

    public static boolean isExists(Path path) {
        return Files.exists(path);
    }

    public static boolean isFile(String path) {
        return isFile(getPath(path));
    }

    public static boolean isFile(Path path) {
        return Files.isRegularFile(path);
    }

    public static boolean isDir(String path) {
        return isDir(getPath(path));
    }

    public static boolean isDir(Path path) {
        return Files.isDirectory(path);
    }

    public static boolean canRead(String path) {
        return canRead(getPath(path));
    }

    public static boolean canRead(Path path) {
        return Files.isReadable(path);
    }

    public static boolean canWrite(String path) {
        return canWrite(getPath(path));
    }

    public static boolean canWrite(Path path) {
        return Files.isWritable(path);
    }

    public static String getContentType(String path) throws IOException {
        return getContentType(getPath(path));
    }

    public static String getContentType(Path path) throws IOException {
        String type;
        URL u = path.toUri().toURL();
        URLConnection uc = u.openConnection();
        type = uc.getContentType();
        return type;
    }

    public static FileType getFileType(String path) throws IOException {
        return getFileType(getPath(path));
    }

    public static FileType getFileType(Path path) throws IOException {
        return ToolFileType.getType(path.toFile());
    }

    public static long getFileLines(String path) throws IOException {
        return getFileLines(getPath(path));
    }

    public static long getFileLines(Path path) throws IOException {
        return Files.size(path);
    }

    //region 基本操作 文件

    public static Path reName(String path, String newName) throws IOException {
        return reName(getPath(path), newName);
    }

    public static Path reName(Path path, String newName) throws IOException {
        Path target = path.getParent().resolve(newName);
        return Files.move(path, target);
    }

    public static Path createDir(String path, boolean replace) throws IOException {
        return createDir(getPath(path), replace);
    }

    public static Path createDir(Path path, boolean replace) throws IOException {
        if (replace) {
            rmr(path);
            return Files.createDirectories(path);
        } else {
            if (isExists(path)) {
                return path;
            } else {
                return Files.createDirectories(path);
            }
        }
    }

    public static Path copy(String src, String dest, boolean replace) throws IOException {
        return copy(getPath(src), getPath(dest), replace);
    }

    public static Path copy(Path src, Path dest, boolean replace) throws IOException {
        dest = getPath(dest.toString(), src.getFileName().toString());
        if (replace) {
            rmr(dest);
            return Files.copy(src, dest);
        } else {
            if (isExists(dest)) {
                return dest;
            } else {
                return Files.copy(src, dest);
            }
        }
    }

    public static Path createFile(String path, boolean replace) throws IOException {
        return createFile(getPath(path), replace);
    }

    public static Path createFile(Path path, boolean replace) throws IOException {
        if (replace) {
            rmr(path);
            return Files.createFile(path);
        } else {
            if (isExists(path)) {
                return path;
            } else {
                return Files.createFile(path);
            }
        }
    }

    public static Path move(String src, String dest, boolean replace) throws IOException {
        return move(getPath(src), getPath(dest), replace);
    }

    public static Path move(Path src, Path dest, boolean replace) throws IOException {
        dest = getPath(dest.toString(), src.getFileName().toString());
        if (replace) {
            rmr(dest);
            return Files.move(src, dest);
        } else {
            if (isExists(dest)) {
                return dest;
            } else {
                return Files.move(src, dest);
            }
        }
    }

    public static void rm(String path) throws IOException {
        rm(getPath(path));
    }

    public static void rm(Path path) throws IOException {
        if (isExists(path)) {
            Files.delete(path);
        }
    }

    public static void rmr(String path) throws IOException {
        rmr(getPath(path));
    }

    public static void rmr(Path path) throws IOException {
        Files.walkFileTree(path, opts, Integer.MAX_VALUE, RmrVisitor.instance);
    }

    public static void visitDir(Path dir, FileVisitor<Path> fileVisitor) throws IOException {
        Files.walkFileTree(dir, opts, Integer.MAX_VALUE, fileVisitor);
    }

    public static Iterator<Path> dirIterator(String dir) {
        return dirIterator(getPath(dir));
    }

    public static Iterator<Path> dirIterator(Path dir) {
        return dir.iterator();
    }

    public static Stream<Path> dirStream(String dir) throws IOException {
        return dirStream(getPath(dir));
    }

    public static Stream<Path> dirStream(Path dir) throws IOException {
        return Files.list(dir);
    }

    //endregion

    //region 文件数据

    public static Path writeStrings(Path path, List<String> stringList, boolean append) throws IOException {
        validWrite(path);

        OpenOption[] options = append ? write_create_append : write_create;
        return Files.write(path, stringList, StrConst.DEFAULT_CHARSET, options);
    }

    public static Path writeBytes(Path path, byte[] bytes, boolean append) throws IOException {
        validWrite(path);

        OpenOption[] options = append ? write_create_append : write_create;
        return Files.write(path, bytes, options);
    }

    public static BufferedWriter writeWriter(Path path, boolean append) throws IOException {
        validWrite(path);

        OpenOption[] options = append ? write_create_append : write_create;
        return Files.newBufferedWriter(path, options);
    }

    public static OutputStream writeOutputStream(Path path, boolean append) throws IOException {
        validWrite(path);

        OpenOption[] options = append ? write_create_append : write_create;
        return Files.newOutputStream(path, options);
    }

    public static Stream<String> readStream(Path path) throws IOException {
        validRead(path);

        return Files.lines(path);
    }

    public static List<String> readStrings(Path path) throws IOException {
        validRead(path);

        return Files.readAllLines(path);
    }

    public static byte[] readBytes(Path path) throws IOException {
        validRead(path);

        return Files.readAllBytes(path);
    }

    public static BufferedReader readReader(Path path) throws IOException {
        validRead(path);

        return Files.newBufferedReader(path);
    }

    public static InputStream readInputStream(Path path) throws IOException {
        validRead(path);

        return Files.newInputStream(path, read);
    }

    private static void validRead(Path path) throws IOException {
        boolean canRead = canRead(path);
        if (!canRead) {
            throw new IOException("该文件无法读取");
        }
    }

    private static void validWrite(Path path) throws IOException {
        boolean canWrite = canWrite(path);
        if (!canWrite) {
            throw new IOException("该文件无法写入");
        }
    }

    //endregion

    //region 文件元数据的修改
    //endregion

    static {
        opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
    }

    public static final EnumSet<FileVisitOption> opts;

    public static final OpenOption[] read = new StandardOpenOption[]{StandardOpenOption.READ};
    public static final OpenOption[] write_create = new StandardOpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE};
    public static final OpenOption[] write_create_append = new StandardOpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND};
}
