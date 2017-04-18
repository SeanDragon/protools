package pro.tools.system;

/**
 * 系统相关工具
 *
 * @author SeanDragon
 */
public final class ToolSystem {
    private ToolSystem() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isWindows() {
        return ToolOS.getOsName().toLowerCase().contains("windows");
    }

}
