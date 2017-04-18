package pro.tools.constant;


import pro.tools.system.ToolSystem;

/**
 * Created on 17/3/17 10:08 星期五.
 * 系统常量类
 *
 * @author SeanDragon
 */
public final class SystemConst {

    private SystemConst() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 是否为Windows系统
     */
    public static final boolean IS_WINDOWS = ToolSystem.isWindows();
}
