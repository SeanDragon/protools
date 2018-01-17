package file;

import com.google.common.collect.Lists;
import org.junit.Test;
import pro.tools.data.text.ToolJson;
import pro.tools.data.text.ToolRandoms;
import pro.tools.data.text.json.TypeBuilder;
import pro.tools.path.ToolPath;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-09-01 11:56
 */
public class TestBasic {

    public static final Path fileName = Paths.get("d:/2.txt");
    public static final Path dirName = Paths.get("d:/2");

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

    @Test
    public void testGson() {
        int count = 1000;
        long begin = 0l;
        List<DDD> ddds = Lists.newArrayListWithCapacity(count);
        for (; count > 0; count--) {
            ddds.add(new DDD().setI(count).setS(ToolRandoms.getRandomStr()));
        }

        System.out.println(ddds);

        String s = ToolJson.anyToJson(ddds);

        begin = System.currentTimeMillis();
        ArrayList<String> arrayList = ToolJson.jsonToArrayList(s);
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        List<Map> maps = ToolJson.jsonToMapList(s);
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        List<DDD> modelList = ToolJson.jsonToModelList(s, DDD.class);
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        List<DDD> any = ToolJson.jsonToAny(s
                , TypeBuilder.newInstance(List.class)
                        .addTypeParam(DDD.class)
                        .build());
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        List<Map> mapList = ToolJson.anyToAny(s, TypeBuilder.newInstance(List.class).addTypeParam(Map.class).build());
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        ;
        String newS = ToolJson.anyToAny(s, String.class);
        System.out.println(System.currentTimeMillis() - begin);

        System.out.println(newS);
    }

}

class DDD {
    private Integer i;
    private String s;

    public Integer getI() {
        return i;
    }

    public DDD setI(Integer i) {
        this.i = i;
        return this;
    }

    public String getS() {
        return s;
    }

    public DDD setS(String s) {
        this.s = s;
        return this;
    }
}