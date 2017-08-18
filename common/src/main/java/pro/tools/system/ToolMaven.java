package pro.tools.system;

import java.io.IOException;

/**
 * Created on 2017/3/20 11:38 星期六.
 * 进行maven的基本操作
 *
 * @author SeanDragon
 */
public final class ToolMaven {
    private ToolMaven() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean install(String groupId, String artifactId, String version, String fileName) throws IOException {
        String mvnInstallStr = "mvn install:install-file -DgroupId=" + groupId + " -DartifactId=" + artifactId + " " +
                "-Dversion=" + version + " -Dfile=" + fileName + " -Dpackaging=jar -DgeneratePom=true";
        ToolShell.ToolCommandExec toolCommandExec = new ToolShell.ToolCommandExec(true, false, mvnInstallStr);
        ToolShell.ToolCommandResult toolCommandResult = ToolShell.execCmd(toolCommandExec);
        return toolCommandResult.errorMsg.length() <= 1;
    }

    public static boolean jar(String filePath) throws IOException {
        String jarStr = "mvn jar:jar";
        ToolShell.ToolCommandExec toolCommandExec = new ToolShell.ToolCommandExec(true, false, " cd " + filePath, jarStr);
        ToolShell.ToolCommandResult toolCommandResult = ToolShell.execCmd(toolCommandExec);
        return toolCommandResult.errorMsg.length() <= 1;
    }
}