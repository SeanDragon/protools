package pro.tools.security.md;

import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD加密组件
 *
 * @author SeanDragon
 */
public final class ToolMD2 {
    private ToolMD2() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * MD2加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static byte[] encodeMD2(byte[] data) throws NoSuchAlgorithmException {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD2");

        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * MD2加密
     *
     * @param data
     *         待加密数据
     *
     * @return byte[] 消息摘要
     *
     * @throws Exception
     */
    public static String encodeMD2Hex(byte[] data) throws NoSuchAlgorithmException {
        // 执行消息摘要
        byte[] b = encodeMD2(data);

        // 做十六进制编码处理
        return new String(Hex.encode(b));
    }

}
