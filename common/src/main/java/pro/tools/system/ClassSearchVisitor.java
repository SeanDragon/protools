package pro.tools.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.constant.StrConst;
import pro.tools.path.visit.RmrVisitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created on 17/9/3 00:00 星期日.
 *
 * @author sd
 */
public class ClassSearchVisitor implements FileVisitor<Path> {

    public static final String CLASS_CONTENT_TYPE = ".class";
    public static final ClassSearchVisitor INSTANCE = new ClassSearchVisitor();
    private static final String CLASS_PATH = StrConst.FILE_SEP + "classes" + StrConst.FILE_SEP;

    private static final Logger log = LoggerFactory.getLogger(RmrVisitor.class);
    private static final String TEST_CLASS_PATH = StrConst.FILE_SEP + "test-classes" + StrConst.FILE_SEP;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    private static boolean getClass(String pathString) {
        int targetClassIndex = pathString.indexOf(CLASS_PATH);
        if (targetClassIndex > 0) {
            targetClassIndex = targetClassIndex + 2 + 7;
        } else if (targetClassIndex < 0) {
            targetClassIndex = pathString.indexOf(TEST_CLASS_PATH);
            if (targetClassIndex > 0) {
                targetClassIndex = targetClassIndex + 2 + 12;
            } else {
                return false;
            }
        }
        pathString = pathString.substring(targetClassIndex, pathString.length() - 6);
        if (ToolSystem.isWindows()) {
            pathString = pathString.replaceAll(StrConst.FILE_SEP + StrConst.FILE_SEP, ".");
        } else {
            pathString = pathString.replaceAll(StrConst.FILE_SEP, ".");
        }
        Class<?> aClass;
        try {
            aClass = Class.forName(pathString);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return ToolClassSearch.getAllClazz().add(aClass);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

        String pathString = file.toString();
        if (!pathString.contains(CLASS_CONTENT_TYPE)) {
            return FileVisitResult.CONTINUE;
        }

        boolean result = getClass(pathString);

        if (log.isDebugEnabled()) {
            log.debug("将Class文件转换为类:\t" + file + ",执行结果:\t" + result);
        }

        return FileVisitResult.CONTINUE;
    }
}
