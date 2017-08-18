package pro.tools.data;

import com.rits.cloning.Cloner;

/**
 * 深度克隆
 *
 * @author SeanDragon
 */
public final class ToolClone {
    private static final Cloner cloner = new Cloner();

    public static <T> T clone(T src) {
        return cloner.deepClone(src);
    }

    public static Cloner getInstance() {
        return cloner;
    }
}