package pro.tools.data;

import org.nustaq.serialization.FSTConfiguration;

/**
 * 序列化操作
 *
 * @author SeanDragon
 */
public final class ToolSerialize {

    private ToolSerialize() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static FSTConfiguration CONFIGURATION = FSTConfiguration
            .createDefaultConfiguration();

    public static <T> byte[] serialize(T value) {
        return CONFIGURATION.asByteArray(value);
    }

    public static <T> T unserialize(byte[] bytes) {
        return (T) CONFIGURATION.asObject(bytes);
    }

    public static synchronized FSTConfiguration getCONFIGURATION() {
        return CONFIGURATION;
    }

    public static synchronized void setCONFIGURATION(FSTConfiguration CONFIGURATION) {
        ToolSerialize.CONFIGURATION = CONFIGURATION;
    }
}
