package sd.http;

import io.netty.util.CharsetUtil;
import org.junit.Test;
import pro.tools.file.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created on 17/4/9 11:20 星期日.
 *
 * @author sd
 */
public class Test_20170409 {
    Path pathDir = Paths.get("/Users/sd");
    Path path = Paths.get("/Users/sd/db.txt");

    @Test
    public void test1() throws IOException {
        List<String> lines = Files.readAllLines(path, CharsetUtil.UTF_8);
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line);
        }
        String fromFile = sb.toString();
        System.out.println(fromFile);
    }

    @Test
    public void test2() throws IOException {

        //printFile(Files.list(Paths.get("/Users/sd")));

        Path fileName = FileUtils.getFileByPath("/Users/sd");
        System.out.println(fileName);

    }

    public void printFile(Stream<Path> list) {
        list.forEach(one -> {
            if (Files.isDirectory(one)) {
                try {
                    printFile(Files.list(one));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(one.toUri());
        });
    }
}
