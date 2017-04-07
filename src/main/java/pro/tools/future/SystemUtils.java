package pro.tools.future;

/**
 * 系统相关工具
 *
 * @author sd
 */
public class SystemUtils {

    public static boolean isWindows() {
        return System.getProperties().get("os.name").toString().toLowerCase().contains("windows");
    }

}
