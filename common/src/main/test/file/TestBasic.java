package file;

import org.junit.Test;
import pro.tools.path.ToolPaths;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-09-01 11:56
 */
public class TestBasic {

    public static final String fileName = "d:/2.txt";
    public static final String dirName = "d:/2";

    @Test
    public void testFileIs() {
        boolean file = ToolPaths.isFile(fileName);
        System.out.println(file);
        boolean dir = ToolPaths.isDir(fileName);
        System.out.println(dir);
        boolean exists = ToolPaths.isExists(fileName);
        System.out.println(exists);
        boolean canWrite = ToolPaths.canWrite(fileName);
        System.out.println(canWrite);
        boolean canRead = ToolPaths.canRead(fileName);
        System.out.println(canRead);
    }

    @Test
    public void testDirIs() {
        boolean file = ToolPaths.isFile(dirName);
        System.out.println(file);
        boolean dir = ToolPaths.isDir(dirName);
        System.out.println(dir);
        boolean exists = ToolPaths.isExists(dirName);
        System.out.println(exists);
        boolean canWrite = ToolPaths.canWrite(dirName);
        System.out.println(canWrite);
        boolean canRead = ToolPaths.canRead(dirName);
        System.out.println(canRead);
    }

    @Test
    public void testCreateDir() throws IOException {
        Path dir = ToolPaths.createDir(Paths.get("d:/2"), true);
        System.out.println(dir);
    }

    @Test
    public void testCreateFile() throws IOException {
        Path file = ToolPaths.createFile(Paths.get("d:/2"), true);
        System.out.println(file);
    }

    @Test
    public void testMove() throws IOException {
        Path move = ToolPaths.move(Paths.get("d:/2"), Paths.get("c:/"), true);
        System.out.println(move);
    }

    @Test
    public void testRmrDir() throws IOException {
        ToolPaths.rmr(Paths.get("d:/2"));
    }

    @Test
    public void testReNameFile() throws IOException {
        Path path = ToolPaths.reName(fileName, "2.txt");
        System.out.println(path);
    }

    @Test
    public void testReNameDir() throws IOException {
        Path path = ToolPaths.reName(dirName, "2");
        System.out.println(path);
    }

}
