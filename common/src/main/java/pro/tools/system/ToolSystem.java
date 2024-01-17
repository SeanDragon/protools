package pro.tools.system;

import java.io.File;

/**
 * 系统相关工具
 *
 * @author SeanDragon
 */
public final class ToolSystem {
    private static Boolean isWindows;
    private static Boolean haveDiskD;

    private ToolSystem() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    public static boolean isWindows() {
        if (isWindows == null) {
            isWindows = ToolOS.getOsName().toLowerCase().contains("windows");
        }
        return isWindows;
    }

    public static boolean haveDiskD() {
        if (haveDiskD == null) {
            if (isWindows()) {
                for (final File file : File.listRoots()) {
                    if (file.isDirectory() && file.getAbsolutePath().toLowerCase().contains("d")) {
                        haveDiskD = true;
                    }
                }
            }
            haveDiskD = false;
        }
        return haveDiskD;
    }
}