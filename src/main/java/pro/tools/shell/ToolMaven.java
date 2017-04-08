package pro.tools.shell;

/**
 * Created on 2017/3/20 11:38 星期六.
 * @author SeanDragon
 */
public class ToolMaven {
    public static boolean install(String groupId, String artifactId, String version, String fileName) {
        String mvnInstallStr = "mvn install:install-file -DgroupId=" + groupId + " -DartifactId=" + artifactId + " " +
                "-Dversion=" + version + " -Dfile=" + fileName + " -Dpackaging=jar -DgeneratePom=true";
        ToolShell.CommandExec commandExec = new ToolShell.CommandExec(true, false, mvnInstallStr);
        ToolShell.CommandResult commandResult = ToolShell.execCmd(commandExec);
        return commandResult.errorMsg.length() <= 1;
    }

    public static boolean jar(String filePath) {
        String jarStr = "mvn jar:jar";
        ToolShell.CommandExec commandExec = new ToolShell.CommandExec(true, false, " cd " + filePath, jarStr);
        ToolShell.CommandResult commandResult = ToolShell.execCmd(commandExec);
        return commandResult.errorMsg.length() <= 1;
    }
}
