package pro.tools.security;

import pro.tools.constant.StrConst;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-09-04 16:16
 */
public final class SecurityUrl {
    public static String encode(String url, Charset charset) {
        try {
            return URLDecoder.decode(url, charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(String url) {
        return encode(url,StrConst.DEFAULT_CHARSET);
    }

    public static String decode(String url, Charset charset) {
        try {
            return URLEncoder.encode(url, charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decode(String url) {
        return decode(url, StrConst.DEFAULT_CHARSET);
    }
}
