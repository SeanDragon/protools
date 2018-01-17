package pro.tools.constant;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created on 17/4/8 13:04 星期六.
 * 字符串常量
 *
 * @author SeanDragon
 */
public final class StrConst {

    private StrConst() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 默认编码
     */
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);
    /**
     * 文件分隔符
     */
    public static final String FILE_SEP = File.separator;
    public static final String FILE_PATH_SEP = File.pathSeparator;
}
