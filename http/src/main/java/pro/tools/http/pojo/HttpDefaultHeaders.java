package pro.tools.http.pojo;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created on 17/9/3 15:19 星期日.
 *
 * @author sd
 */
public class HttpDefaultHeaders {
    private final static Map<String, Object> DEFAULT_HEADERS;

    static {
        DEFAULT_HEADERS = Maps.newHashMap();
        init();
    }

    private static void init() {
        DEFAULT_HEADERS.put("connection", "keep-alive");
        DEFAULT_HEADERS.put("accept-encoding", "gzip,deflate");
        DEFAULT_HEADERS.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3191.0 Safari/537.36");
        DEFAULT_HEADERS.put("DNT", 1);
        DEFAULT_HEADERS.put("cache-control", "max-age=0");
    }

    public static Map<String, Object> getDefaultHeaders() {
        return DEFAULT_HEADERS;
    }
}
