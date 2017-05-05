package pro.tools.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 使用java.lang.Serializable来实现对象的深度克隆
 *
 * @author SeanDragon
 */
public final class ToolClone {
    public static <T> T clone(T src) throws IOException, ClassNotFoundException {
        T dist;
        try (ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream()) {
            try (ObjectOutputStream out = new ObjectOutputStream(memoryBuffer)) {
                out.writeObject(src);
                out.flush();
            }
            try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()))) {
                dist = (T) in.readObject();
            }
        }
        return dist;
    }
}