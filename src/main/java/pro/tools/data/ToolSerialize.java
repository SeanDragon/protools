package pro.tools.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.data.text.ToolRandoms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化操作
 *
 * @author SeanDragon
 */
public final class ToolSerialize {

    private static final Logger log = LoggerFactory.getLogger(ToolRandoms.class);

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) throws IOException {
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);) {
            // 序列化
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);) {
            // 序列化
            return objectInputStream.readObject();
        }
    }

}
