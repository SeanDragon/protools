package redis;

import java.io.Serializable;

/**
 * Created by steven on 2016/11/25.
 */
public interface TaskInterface extends Serializable {
    int execute(Object... objs);
}
