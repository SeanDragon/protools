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

    public static final String classContentType = ".class";

    private static final String classPath = StrConst.FILE_SEP + "classes" + StrConst.FILE_SEP;
    private static final String testClassPath = StrConst.FILE_SEP + "test-classes" + StrConst.FILE_SEP;

    private static final Logger log = LoggerFactory.getLogger(RmrVisitor.class);

    public static final ClassSearchVisitor instance = new ClassSearchVisitor();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        String pathString = file.toString();
        if (!pathString.contains(classContentType)) {
            return FileVisitResult.CONTINUE;
        }

        boolean result = getClass(pathString);

        log.debug("将Class文件转换为类:\t" + file + ",执行结果:\t" + result);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    private static boolean getClass(String pathString) {
        int target_class_index = pathString.indexOf(classPath);
        if (target_class_index > 0) {
            target_class_index = target_class_index + 2 + 7;
        } else if (target_class_index < 0) {
            target_class_index = pathString.indexOf(testClassPath);
            if (target_class_index > 0) {
                target_class_index = target_class_index + 2 + 12;
            } else {
                return false;
            }
        }
        pathString = pathString.substring(target_class_index, pathString.length() - 6);
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
}
