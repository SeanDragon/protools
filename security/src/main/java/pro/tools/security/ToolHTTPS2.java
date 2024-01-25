package pro.tools.security;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * HTTPS组件
 *
 * @author SeanDragon
 */
public final class ToolHTTPS2 {
    private ToolHTTPS2() {
        throw new UnsupportedOperationException("我是工具类，别初始化我。。。");
    }

    /**
     * 获得KeyStore
     *
     * @param keyStorePath
     *         密钥库路径
     * @param password
     *         密码
     *
     * @return KeyStore 密钥库
     *
     * @throws Exception
     */
    private static KeyStore getKeyStore(String keyStorePath, String password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        // 实例化密钥库
        KeyStore ks = KeyStore.getInstance("PKCS12");

        // 获得密钥库文件流
        FileInputStream is = new FileInputStream(keyStorePath);

        // 加载密钥库
        ks.load(is, password.toCharArray());

        // 关闭密钥库文件流
        is.close();

        return ks;
    }

    /**
     * 获得SSLSocektFactory
     *
     * @param password
     *         密码
     * @param keyStorePath
     *         密钥库路径
     * @param trustStorePath
     *         信任库路径
     *
     * @return SSLSocketFactory
     *
     * @throws Exception
     */
    private static SSLSocketFactory getSSLSocketFactory(String password, String keyStorePath, String trustStorePath) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return ToolHTTPS.getSSLSocketFactory(password, keyStorePath, trustStorePath);
    }

    /**
     * 为HttpsURLConnection配置SSLSocketFactory
     *
     * @param conn
     *         HttpsURLConnection
     * @param password
     *         密码
     * @param keyStorePath
     *         密钥库路径
     * @param trustKeyStorePath
     *         信任库路径
     *
     * @throws Exception
     */
    public static void configSSLSocketFactory(HttpsURLConnection conn, String password, String keyStorePath,
                                              String trustKeyStorePath) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        // 获得SSLSocketFactory
        SSLSocketFactory sslSocketFactory = getSSLSocketFactory(password, keyStorePath, trustKeyStorePath);

        // 设置SSLSocketFactory
        conn.setSSLSocketFactory(sslSocketFactory);
    }

}
