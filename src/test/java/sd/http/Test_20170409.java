package sd.http;

import org.junit.Test;
import pro.tools.constant.StrConst;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created on 17/4/9 11:20 星期日.
 *
 * @author SeanDragon
 */
public class Test_20170409 {
    Path pathDir = Paths.get("/Users/sd");
    Path path = Paths.get("/Users/sd/db.txt");

    @Test
    public void test1() throws IOException {
        List<String> lines = Files.readAllLines(path, StrConst.DEFAULT_CHARSET);
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line);
        }
        String fromFile = sb.toString();
        System.out.println(fromFile);
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
