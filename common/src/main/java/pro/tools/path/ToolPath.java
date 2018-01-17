package pro.tools.path;

import pro.tools.constant.StrConst;
import pro.tools.data.text.ToolStr;
import pro.tools.file.FileType;
import pro.tools.file.ToolFileType;
import pro.tools.path.visit.RmrVisitor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    /**
     * 文件遍历的访问选项
     */
    private static final EnumSet<FileVisitOption> OPTS = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

    /**
     * 读取选项
     */
    private static final OpenOption[] READ = new StandardOpenOption[]{StandardOpenOption.READ};
    /**
     * 写入选项
     */
    private static final OpenOption[] WRITE_CREATE = new StandardOpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE};
    private static final OpenOption[] WRITE_CREATE_APPEND = new StandardOpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND};

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

    public static boolean isExists(Path path) {
        return Files.exists(path);
    }

    public static boolean isFile(Path path) {
        return Files.isRegularFile(path);
    }

    public static boolean isDir(Path path) {
        return Files.isDirectory(path);
    }

    public static boolean canRead(Path path) {
        return Files.isReadable(path);
    }

    public static boolean canWrite(Path path) {
        return Files.isWritable(path);
    }

    public static String getContentType(Path path) throws IOException {
        String type;
        URL u = path.toUri().toURL();
        URLConnection uc = u.openConnection();
        type = uc.getContentType();
        return type;
    }

    public static FileType getFileType(Path path) throws IOException {
        return ToolFileType.getType(path.toFile());
    }

    public static long getFileLines(Path path) throws IOException {
        return Files.size(path);
    }

    //region 基本操作 文件

    public static Path reName(Path path, String newName) throws IOException {
        Path target = path.getParent().resolve(newName);
        return Files.move(path, target);
    }

    public static Path createDir(Path path, boolean replace) throws IOException {
        if (replace) {
            rmr(path);
            if (isDir(path)) {
                path = path.getParent();
            }
            return Files.createDirectories(path);
        } else {
            if (isExists(path)) {
                return path;
            } else {
                if (isDir(path)) {
                    path = path.getParent();
                }
                return Files.createDirectories(path);
            }
        }
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

    public static void rm(Path path) throws IOException {
        if (isExists(path)) {
            Files.delete(path);
        }
    }

    public static void rmr(Path path) throws IOException {
        Files.walkFileTree(path, OPTS, Integer.MAX_VALUE, RmrVisitor.INSTANCE);
    }

    public static Iterator<Path> dirIterator(Path dir) {
        return dir.iterator();
    }

    public static Stream<Path> dirStream(Path dir) throws IOException {
        return Files.list(dir);
    }

    //endregion

    //region 文件数据

    public static void visitDir(Path dir, FileVisitor<Path> fileVisitor) throws IOException {
        Files.walkFileTree(dir, OPTS, Integer.MAX_VALUE, fileVisitor);
    }

    public static Stream<String> readStream(Path path) throws IOException {
        return Files.lines(path);
    }

    public static List<String> readStrings(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    public static byte[] readBytes(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    public static InputStream readInputStream(Path path) throws IOException {
        return Files.newInputStream(path, READ);
    }

    public static BufferedReader readReader(Path path) throws IOException {
        return Files.newBufferedReader(path);
    }

    public static Path writeStrings(Path path, List<String> stringList, boolean append) throws IOException {
        OpenOption[] options = append ? WRITE_CREATE_APPEND : WRITE_CREATE;
        return Files.write(path, stringList, StrConst.DEFAULT_CHARSET, options);
    }

    public static Path writeBytes(Path path, byte[] bytes, boolean append) throws IOException {
        OpenOption[] options = append ? WRITE_CREATE_APPEND : WRITE_CREATE;
        return Files.write(path, bytes, options);
    }

    public static OutputStream writeOutputStream(Path path, boolean append) throws IOException {
        OpenOption[] options = append ? WRITE_CREATE_APPEND : WRITE_CREATE;
        return Files.newOutputStream(path, options);
    }

    public static BufferedWriter writeWriter(Path path, boolean append) throws IOException {
        OpenOption[] options = append ? WRITE_CREATE_APPEND : WRITE_CREATE;
        return Files.newBufferedWriter(path, options);
    }
    //endregion

    //region 文件元数据的修改
    //TODO
    //endregion
}
