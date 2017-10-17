package pro.tools.format;

/**
 * 格式化工具，数字、字符串、Object类型之间的常用转换 Created by Administrator on 2016/3/7.
 *
 * @author Steven Duan
 * @version 1.0
 */
public final class ToolFormat {

    private ToolFormat() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断是否为空 ,
     *
     * @param arg
     *         要判断的值
     *
     * @return 为空返回true, 否则返回false
     */
    public static boolean isEmpty(Object arg) {
        return toStringTrim(arg).length() == 0;
    }

    /**
     * Object 转换成 String 为null 返回空字符,使用:toString(值,默认值[选填])
     *
     * @param args
     *         参数序列
     *
     * @return 返回转换后的String
     */
    public static String toString(Object... args) {
        String def = "";
        if (args != null) {
            if (args.length > 1) {
                def = toString(args[args.length - 1]);
            }
            Object obj = args[0];
            if (obj == null) {
                return def;
            }
            return obj.toString();
        } else {
            return def;
        }
    }

    /**
     * Object 转换成 String[去除所以空格]; 为null 返回空字符,使用:toStringTrim(值,默认值[选填])
     *
     * @param args
     *         参数序列
     *
     * @return 返回转换后的String
     */
    public static String toStringTrim(Object... args) {
        String str = toString(args);
        return str.replaceAll("\\s*", "");
    }

    /**
     * 获取人性化的异常信息
     *
     * @param throwable
     *         异常
     *
     * @return 信息
     */
    public static String toException(Throwable throwable) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        StackTraceElement ste = (stackTrace != null && stackTrace.length > 0) ? stackTrace[0] : null;
        if (ste != null) {
            return String.format("%s - [%s] [%s.%s(%s)]", throwable.getMessage(), ste.getFileName(), ste.getClassName(), ste.getMethodName(), ste.getLineNumber());
        } else {
            return throwable.getMessage();
        }
    }

}