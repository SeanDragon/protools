package pro.tools.system;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * 系统环境相关
 * 主要是系统属性信息的key
 *
 * @author SeanDragon
 */
public final class ToolOS {
    public static final String JAVA_VERSION = getOsSystemProperty("java.version"); // Java的运行环境版本
    public static final String JAVA_VENDO = getOsSystemProperty("java.vendor"); // Java的运行环境供应商
    public static final String JAVA_VENDO_URL = getOsSystemProperty("java.vendor.url"); // Java供应商的URL
    public static final String JAVA_HOME = getOsSystemProperty("java.home"); // Java的安装路径
    public static final String JAVA_VM_SPECIFICATION_VERSION = getOsSystemProperty("java.vm.specification.version"); // Java的虚拟机规范版本
    public static final String JAVA_VM_SPECIFICATION_VENDOR = getOsSystemProperty("java.vm.specification.vendor"); // Java的虚拟机规范供应商
    public static final String JAVA_VM_SPECIFICATION_NAME = getOsSystemProperty("java.vm.specification.name"); // Java的虚拟机规范名称
    public static final String JAVA_VM_VERSION = getOsSystemProperty("java.vm.version"); // Java的虚拟机实现版本
    public static final String JAVA_VM_VENDOR = getOsSystemProperty("java.vm.vendor"); // Java的虚拟机实现供应商
    public static final String JAVA_VM_NAME = getOsSystemProperty("java.vm.name"); // Java的虚拟机实现名称
    public static final String JAVA_SPECIFICATION_VERSION = getOsSystemProperty("java.specification.version"); // Java运行时环境规范版本
    public static final String JAVA_SPECIFICATION_VENDER = getOsSystemProperty("java.specification.vender"); // Java运行时环境规范供应商
    public static final String JAVA_SPECIFICATION_NAME = getOsSystemProperty("java.specification.name"); // Java运行时环境规范名称
    public static final String JAVA_CLASS_VERSION = getOsSystemProperty("java.class.version"); // Java的类格式版本号
    public static final String JAVA_CLASS_PATH = getOsSystemProperty("java.class.path"); // Java的类路径
    public static final String JAVA_LIBRARY_PATH = getOsSystemProperty("java.library.path"); // 加载库时搜索的路径列表
    public static final String JAVA_IO_TMPDIR = getOsSystemProperty("java.io.tmpdir"); // 默认的临时文件路径
    public static final String JAVA_EXT_DIRS = getOsSystemProperty("java.ext.dirs"); // 一个或多个扩展目录的路径
    public static final String OS_NAME = getOsSystemProperty("os.name"); // 操作系统的名称
    public static final String OS_ARCH = getOsSystemProperty("os.arch"); // 操作系统的构架
    public static final String OS_VERSION = getOsSystemProperty("os.version"); // 操作系统的版本
    public static final String FILE_SEPARATOR = getOsSystemProperty("java.file.separator"); // 文件分隔符
    public static final String PATH_SEPARATOR = getOsSystemProperty("path.separator"); // 路径分隔符
    public static final String LINE_SEPARATOR = getOsSystemProperty("line.separator"); // 行分隔符
    public static final String USER_NAME = getOsSystemProperty("user.name"); // 用户的账户名称
    public static final String USER_HOME = getOsSystemProperty("user.home"); // 用户的主目录
    public static final String USER_DIR = getOsSystemProperty("user.dir"); //  用户的当前工作目录
    // 系统bean
    private static final OperatingSystemMXBean SYSTEM_MX_BEAN;
    private static final List<GarbageCollectorMXBean> LIST;
    // K转换M
    private static final long K2M = 1024L * 1024L;

    static {
        SYSTEM_MX_BEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        LIST = ManagementFactory.getGarbageCollectorMXBeans();
    }

    private ToolOS() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 获取java系统环境变量
     *
     * @param key
     *
     * @return
     */
    public static String getOsSystemProperty(String key) {
        return System.getProperty(key);
    }

    /**
     * 获取本机IP
     *
     * @return
     */
    public static String getOsLocalHostIp() throws UnknownHostException {
        return getInetAddress().getHostAddress();// 获得本机IP
    }

    /**
     * 获取本机名称
     *
     * @return
     */
    public static String getOsLocalHostName() throws UnknownHostException {
        return getInetAddress().getHostName();// 获得本机名称
    }

    public static InetAddress getInetAddress() throws UnknownHostException {
        InetAddress inetAddress;
        inetAddress = InetAddress.getLocalHost();
        return inetAddress;
    }

    /**
     * 获取操作系统路径类型
     *
     * @return
     */
    public static String getOsPathType() {
        String osPathType = FILE_SEPARATOR;
        if ("\\".equals(osPathType)) {
            return "\\\\";
        }
        if ("/".equals(osPathType)) {
            return "/";
        }
        return null;
    }

    /**
     * 获取操作系统类型名称
     *
     * @return
     */
    public static String getOsName() {
        return SYSTEM_MX_BEAN.getName();// System.getProperty("os.name");
    }

    /**
     * 操作系统的体系结构 如:x86
     *
     * @return
     */
    public static String getOsArch() {
        return SYSTEM_MX_BEAN.getArch();// System.getProperty("os.arch");
    }

    /**
     * 获取CPU数量
     *
     * @return
     */
    public static int getOsCpuNumber() {
        return SYSTEM_MX_BEAN.getAvailableProcessors();// Runtime.getRuntime().availableProcessors();// 获取当前电脑CPU数量
    }

    /**
     * cpu使用率
     *
     * @return
     */
    public static double getOsCpuRatio() {
        return SYSTEM_MX_BEAN.getSystemCpuLoad();
    }

    /**
     * 物理内存，总的可使用的，单位：M
     *
     * @return
     */
    public static long getOsPhysicalMemory() {
        long totalMemorySize = SYSTEM_MX_BEAN.getTotalPhysicalMemorySize() / K2M; // M
        return totalMemorySize;
    }

    /**
     * 物理内存，剩余，单位：M
     *
     * @return
     */
    public static long getOsPhysicalFreeMemory() {
        return SYSTEM_MX_BEAN.getFreePhysicalMemorySize() / K2M;
    }

    /**
     * JVM内存，内存总量，单位：M
     *
     * @return
     */
    public static long getJvmTotalMemory() {
        return Runtime.getRuntime().totalMemory() / K2M;
    }

    /**
     * JVM内存，空闲内存量，单位：M
     *
     * @return
     */
    public static long getJvmFreeMemory() {
        return Runtime.getRuntime().freeMemory() / K2M;
    }

    /**
     * JVM内存，最大内存量，单位：M
     *
     * @return
     */
    public static long getJvmMaxMemory() {
        return Runtime.getRuntime().maxMemory() / K2M;
    }

    /**
     * 获取JVM GC次数
     *
     * @return
     */
    public static long getJvmGcCount() {
        long count = 0;
        for (final GarbageCollectorMXBean garbageCollectorMXBean : LIST) {
            count += garbageCollectorMXBean.getCollectionCount();
        }
        return count;
    }

    /**
     * 系统线程列表
     *
     * @return
     */
    public static List<Thread> getJvmThreads() {
        int activeCount = Thread.activeCount();
        Thread[] threads = new Thread[activeCount];
        Thread.enumerate(threads);
        return Arrays.asList(threads);
    }

}
