package pro.tools.system;

import pro.tools.constant.StrConst;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 17/4/11 22:43 星期二.
 *
 * @author sd
 */
public final class ToolClassSearch {
    private static Set<Class> classList = new HashSet<>();

    static {
        init();
    }

    private ToolClassSearch() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init() {
        String file = ToolPosition.getRootClassPath();

        File classFile = new File(file);

        tree(classFile);
    }

    private static void tree(File file) {
        if (file.isDirectory() && file.listFiles() != null) {
            for (File oneFile : file.listFiles()) {
                tree(oneFile);
            }
        } else {
            if (file.getName().contains(".class")) {
                try {
                    String path = file.getAbsolutePath();
                    int target_class_index = path.indexOf(StrConst.FILE_SEP + "classes" + StrConst.FILE_SEP);
                    if (target_class_index > 0) {
                        target_class_index = target_class_index + 2 + 7;
                    } else if (target_class_index < 0) {
                        target_class_index = path.indexOf(StrConst.FILE_SEP + "test-classes" + StrConst.FILE_SEP);
                        if (target_class_index > 0) {
                            target_class_index = target_class_index + 2 + 12;
                        } else {
                            return;
                        }
                    }
                    path = path.substring(target_class_index, path.length() - 6);
                    if (ToolSystem.isWindows()) {
                        path = path.replaceAll(StrConst.FILE_SEP + StrConst.FILE_SEP, ".");
                    } else {
                        path = path.replaceAll(StrConst.FILE_SEP, ".");
                    }
                    Class<?> aClass = Class.forName(path);
                    classList.add(aClass);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Set<Class> getAllClazz() {
        return classList;
    }

    public static Set<Class<?>> getClazz(Class<?> clazz) {
        boolean contains = classList.contains(clazz);
        if (contains) {
            Set<Class<?>> returnClassList = new HashSet<>();
            classList.forEach(one -> {
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

        classList.forEach(one -> {
            if (one.isAnnotationPresent(clazz)) {
                returnClassList.add(one);
            }
        });

        return returnClassList;
    }

    public static Set<Class<?>> getClazzByInterface(Class<?> clazz) {
        if (!clazz.isInterface()) return null;
        Set<Class<?>> returnClassList = new HashSet<>();

        classList.forEach(one -> {
            if (clazz.isAssignableFrom(one)) {
                if (!clazz.equals(one)) {// 本身加不进去
                    returnClassList.add(one);
                }
            }
        });

        return returnClassList;
    }
}
