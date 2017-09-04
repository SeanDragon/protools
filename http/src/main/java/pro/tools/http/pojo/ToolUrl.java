package pro.tools.http.pojo;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-09-04 11:19
 */
public class ToolUrl {
    public static String getHostAndPort(String urlString) throws HttpException {
        try {
            URL url = new URL(urlString);
            String host = url.getHost();
            int port = url.getPort();
            if (port == -1) {
                port = url.getDefaultPort();
            }
            return host + port;
        } catch (MalformedURLException e) {
            throw new HttpException("URL is ERROR", e);
        }
    }
}
