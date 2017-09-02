package pro.tools.system;

import pro.tools.path.ToolPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 17/4/11 22:43 星期二.
 *
 * @author sd
 */
public final class ToolClassSearch {
    private static Set<Class> classSet;

    static {
        init();
    }

    private ToolClassSearch() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init() {
        classSet = new HashSet<>();
        try {
            Path path = ToolPath.getPath(ToolPosition.getRootClassPath());
            Files.walkFileTree(path, ToolPath.opts, Integer.MAX_VALUE, ClassSearchVisitor.instance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static Set<Class> getAllClazz() {
        return classSet;
    }

    public static Set<Class<?>> getClazz(Class<?> clazz) {
        boolean contains = classSet.contains(clazz);
        if (contains) {
            Set<Class<?>> returnClassList = new HashSet<>();
            classSet.forEach(one -> {
                if (one.getName().equals(clazz.getName())) {
                    returnClassList.add(one);
                } else {
                    Class<?> superclass = one.getSuperclass();
                    if (superclass != null && superclass.getName().equals(clazz.getName())) {
                        returnClassList.add(one);
                    }

                    Class<?>[] interfaces = one.getInterfaces();
                    for (Class<?> oneInterface : interfaces) {
                        if (oneInterface.getName().equals(clazz.getName())) {
                            returnClassList.add(one);
                        }
                    }
                }
            });
            return returnClassList;
        } else {
            return null;
        }
    }

    public static Set<Class<?>> getClazzByAnnotation(Class<?> clazz) {
        if (!clazz.isAnnotation()) return null;
        Set<Class<?>> returnClassList = new HashSet<>();

        classSet.forEach(one -> {
            if (one.isAnnotationPresent(clazz)) {
                returnClassList.add(one);
            }
        });

        return returnClassList;
    }

    public static Set<Class<?>> getClazzByInterface(Class<?> clazz) {
        if (!clazz.isInterface()) return null;
        Set<Class<?>> returnClassList = new HashSet<>();

        classSet.forEach(one -> {
            if (clazz.isAssignableFrom(one)) {
                if (!clazz.equals(one)) {// 本身加不进去
                    returnClassList.add(one);
                }
            }
        });

        return returnClassList;
    }
}
