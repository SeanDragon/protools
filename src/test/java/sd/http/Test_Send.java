package sd.http;

import com.google.common.base.MoreObjects;
import org.junit.Test;
import pro.tools.data.ToolClone;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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
    public void test4() throws IOException, ClassNotFoundException {
        long begin = System.currentTimeMillis();
        UserModel userModel1 = new UserModel();
        Map map = new HashMap();
        map.put("a", "b");
        userModel1.setId("1")
                .setUserName("user1")
                .setMap(map);

        UserModel userModel3 = new UserModel();
        userModel3.setUserName("user3")
                .setId("3")
                .setUserModel(userModel1);
        UserModel clone = ToolClone.clone(userModel3);
        System.out.println(userModel3.hashCode());
        System.out.println(clone.hashCode());

        System.err.println(System.currentTimeMillis() - begin);
    }
}

class UserModel implements Serializable {
    private String id;
    private String userName;
    private Map<String, String> map;
    private UserModel userModel;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("userName", userName)
                .add("map", map)
                .add("userModel", userModel)
                .toString();
    }

    public String getId() {
        return id;
    }

    public UserModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public UserModel setMap(Map<String, String> map) {
        this.map = map;
        return this;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public UserModel setUserModel(UserModel userModel) {
        this.userModel = userModel;
        return this;
    }
}