package sd.http;

import org.junit.Test;
import pro.tools.file.FileUtils;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created on 17/4/9 09:58 星期日.
 *
 * @author SeanDragon
 */
public class Test_Send {
    @Test
    public void test1() {
    }

    private String filePath = "/Users/sd/9999.txt";
    private String dirPath = "/Users/sd/a/b／1.txt";

    @Test
    public void test2() throws IOException {

        //for (int i = 0; i < 10000; i++) {
        //    Path rename = FileUtils.rename(filePath, i + ".txt");
        //    if (rename != null) {
        //        filePath = rename.toString();
        //    }
        //}

        Stream<Path> walk = Files.walk(Paths.get(dirPath), FileVisitOption.FOLLOW_LINKS);

        walk.filter(Files::isExecutable).forEach(System.out::println);
    }

    @Test
    public void test3() {
        try {
            Path orExistsFile = FileUtils.createOrExistsFile(dirPath);
            System.out.println(orExistsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
