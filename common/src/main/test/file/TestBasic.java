package file;

import org.junit.Test;
import pro.tools.path.ToolPath;

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
        boolean file = ToolPath.isFile(fileName);
        System.out.println(file);
        boolean dir = ToolPath.isDir(fileName);
        System.out.println(dir);
        boolean exists = ToolPath.isExists(fileName);
        System.out.println(exists);
        boolean canWrite = ToolPath.canWrite(fileName);
        System.out.println(canWrite);
        boolean canRead = ToolPath.canRead(fileName);
        System.out.println(canRead);
    }

    @Test
    public void testDirIs() {
        boolean file = ToolPath.isFile(dirName);
        System.out.println(file);
        boolean dir = ToolPath.isDir(dirName);
        System.out.println(dir);
        boolean exists = ToolPath.isExists(dirName);
        System.out.println(exists);
        boolean canWrite = ToolPath.canWrite(dirName);
        System.out.println(canWrite);
        boolean canRead = ToolPath.canRead(dirName);
        System.out.println(canRead);
    }

    @Test
    public void testCreateDir() throws IOException {
        Path dir = ToolPath.createDir(Paths.get("d:/2"), true);
        System.out.println(dir);
    }

    @Test
    public void testCreateFile() throws IOException {
        Path file = ToolPath.createFile(Paths.get("d:/2"), true);
        System.out.println(file);
    }

    @Test
    public void testMove() throws IOException {
        Path move = ToolPath.move(Paths.get("d:/2"), Paths.get("c:/"), true);
        System.out.println(move);
    }

    @Test
    public void testRmrDir() throws IOException {
        ToolPath.rmr(Paths.get("d:/2"));
    }

    @Test
    public void testReNameFile() throws IOException {
        Path path = ToolPath.reName(fileName, "2.txt");
        System.out.println(path);
    }

    @Test
    public void testReNameDir() throws IOException {
        Path path = ToolPath.reName(dirName, "2");
        System.out.println(path);
    }

}
