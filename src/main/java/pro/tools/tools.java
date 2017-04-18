package pro.tools;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 快捷工具，常用检测
 * Created by Administrator on 2016/3/7.
 *
 * @author Steven Duan
 * @version 1.0
 */
public final class tools {

    /**
     * 检测是否是移动设备访问
     *
     * @param userAgent 浏览器标识
     * @return true:移动设备接入，false:pc端接入
     */
    public static boolean isMobile(String userAgent) {
        if (null == userAgent) {
            userAgent = "";
        }
        // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
        // 字符串在编译时会被转码一次,所以是 "\\b"
        // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
                + "|windows (phone|ce)|blackberry"
                + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
                + "|laystation portable)|nokia|fennec|htc[-_]"
                + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(nexus 7)|up.browser"
                + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        //移动设备正则匹配：手机端、平板
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            //移动设备访问，返回true
            return true;
        } else {
            //PC端访问，返回false
            //测试环境返回 true
            //为方便后期阅读和修改, 保持if结构
            return true;
        }
    }

    /**
     * 生成随机整数
     *
     * @param length 长度在1-10之间
     * @return 返回length长度的随机整数
     */
    public static int getCode(int length) {
//        if (length < 1)
//            length = 1;
//        if (length > 10)
//            length = 10;
//        return (int)((Math.random()*9+1)*Math.pow(10,length-1));
        try {
            if (length <= 0) {
                return 0;
            }
            Random r = new Random();
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < length; i++) {
                result.append(Integer.toString(r.nextInt(10)));
            }
            return Integer.valueOf(result.toString());
        } catch (Exception e) {

        }
        return 0;
    }

    /**
     * 生成随机数字符串
     *
     * @param length 任意长度
     * @return 返回length长度的随机数字符串
     */
    public static String getCodeStr(int length) {
        try {
            if (length <= 0) {
                return "";
            }
            Random r = new Random();
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < length; i++) {
                result.append(Integer.toString(r.nextInt(10)));
            }
            return result.toString();
        } catch (Exception e) {

        }
        return "";
    }

    /**
     *
     */
    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    //读取配置文件, 仅识别int和java.lang.String
    public static void bindProperties(String filename, Object object) {
        ResourceBundle.clearCache();
        Class clazz = object.getClass();
        ResourceBundle mongoBundle = ResourceBundle.getBundle(filename);
        Enumeration<String> enumkeys = mongoBundle.getKeys();
        while (enumkeys.hasMoreElements()) {
            String k = enumkeys.nextElement();
            String[] keys = k.split("\\.");
            String key = keys[keys.length - 1];
            try {
                Field f = clazz.getDeclaredField(key);
                if (f != null) {
                    f.setAccessible(true);
                    String typeName = f.getGenericType().getTypeName();
                    if (typeName.equals("java.lang.String")) {
                        f.set(object, f.getType().getConstructor(f.getType()).newInstance(mongoBundle.getString(k)));
                    } else if (typeName.equals("int") || typeName.equals("java.lang.Integer")) {
                        f.set(object, Integer.parseInt(mongoBundle.getString(k)));
                    } else if (typeName.equals("double") || typeName.equals("java.lang.Double")) {
                        f.set(object, Double.parseDouble(mongoBundle.getString(k)));
                    } else if (typeName.equals("boolean") || typeName.equals("java.lang.Boolean")) {
                        f.set(object, Boolean.parseBoolean(mongoBundle.getString(k)));
                    }
                    f.setAccessible(false);
                }
            } catch (InstantiationException e) {

            } catch (InvocationTargetException e) {

            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException e) {

            } catch (NoSuchFieldException e) {

            }
        }

//        for(Field f : object.getClass().getDeclaredFields()){
//            f.setAccessible(true);
//            try {
//                if (f.getGenericType().getTypeName().equals("java.lang.String")) {
//                    f.set(object, f.getType().getConstructor(f.getType()).newInstance(mongoBundle.getString(f.getName())));
//                }else if (f.getGenericType().getTypeName().equals("int")){
//                    f.set(object, Integer.parseInt(mongoBundle.getString(f.getName())));
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            f.setAccessible(false);
//        }


    }

    public static String toException(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        StackTraceElement ste = (stackTrace != null && stackTrace.length > 0) ? stackTrace[0] : null;
        if (ste != null)
            return String.format("%s - [%s] [%s.%s(%s)]", e.getMessage(), ste.getFileName(), ste.getClassName(), ste.getMethodName(), ste.getLineNumber());
            //return String.ToolFormat("%s - [%s(%s)]", e.getMessage(), ste.getFileName(), ste.getLineNumber());
        else
            return e.getMessage();
    }
}
