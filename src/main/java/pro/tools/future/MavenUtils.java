package pro.tools.future;

/**
 * Created by SeanDragon on 2017/3/20.
 */
public class MavenUtils {
    public static boolean install(String groupId, String artifactId, String version, String fileName) {
        String mvnInstallStr = "mvn install:install-file -DgroupId=" + groupId + " -DartifactId=" + artifactId + " " +
                "-Dversion=" + version + " -Dfile=" + fileName + " -Dpackaging=jar -DgeneratePom=true";
        ShellUtils.CommandExec commandExec = new ShellUtils.CommandExec(true, false, mvnInstallStr);
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(commandExec);
        return commandResult.errorMsg.length() <= 1;
    }

    public static boolean jar(String filePath) {
        String jarStr = "mvn jar:jar";
        ShellUtils.CommandExec commandExec = new ShellUtils.CommandExec(true, false, " cd " + filePath, jarStr);
        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(commandExec);
        return commandResult.errorMsg.length() <= 1;
    }
}
