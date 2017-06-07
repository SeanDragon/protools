package pro.tools.data;

import com.rits.cloning.Cloner;

/**
 * 深度克隆
 *
 * @author SeanDragon
 */
public final class ToolClone {
    public static <T> T clone(T src) {
        Cloner cloner = new Cloner();
        return cloner.deepClone(src);
    }
}