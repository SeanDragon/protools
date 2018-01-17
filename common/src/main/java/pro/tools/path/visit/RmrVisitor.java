package pro.tools.path.visit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-09-01 14:47
 */
public class RmrVisitor implements FileVisitor<Path> {

    private static final Logger log = LoggerFactory.getLogger(RmrVisitor.class);

    public static final RmrVisitor INSTANCE = new RmrVisitor();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        boolean result = rm(file);
        if (log.isDebugEnabled()) {
            log.debug("删除文件:\t" + file + ",执行结果:\t" + result);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (exc == null) {
            boolean success = rm(dir);
            if (log.isDebugEnabled()) {
                log.debug("删除文件夹:\t" + dir + ",执行结果:\t" + success);
            }
        } else {
            throw exc;
        }
        return FileVisitResult.CONTINUE;
    }

    private static boolean rm(Path path) throws IOException {
        return Files.deleteIfExists(path);
    }
}
