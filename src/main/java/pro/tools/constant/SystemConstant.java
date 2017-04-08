package pro.tools.constant;


import io.netty.util.CharsetUtil;
import pro.tools.future.ToolSystem;

/**
 * Created on 17/3/17 10:08 星期五.
 *
 * @author sd
 */
public class SystemConstant {
    public static final boolean IS_WINDOWS = ToolSystem.isWindows();
    public static final String DEFAULT_CHARSET = CharsetUtil.UTF_8.displayName();
}
