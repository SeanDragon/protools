package pro.tools.system;

import pro.tools.constant.SystemConst;

import java.io.File;
import java.io.IOException;

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

    public static boolean haveDiskD() throws IOException {
        if (SystemConst.IS_WINDOWS) {
            File[] files = File.listRoots();
            for (File file : files) {
                if (file.isDirectory() && file.getAbsolutePath().contains("D")) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
}