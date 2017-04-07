package pro.tools.http2;

import com.ning.http.client.cookie.Cookie;

import java.util.List;

/**
 * http响应实体
 *
 * @author SeanDragon
 *         Create By 2017-04-07 9:06
 */
public final class ResponseBody {
    private boolean haveError;
    private String errMsg;
    private int statusCode;
    private String statusText;
    private String responseBody;
    private List<Cookie> responseCookieList;

    public boolean isHaveError() {
        return haveError;
    }

    public ResponseBody setHaveError(boolean haveError) {
        this.haveError = haveError;
        return this;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public ResponseBody setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ResponseBody setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusText() {
        return statusText;
    }

    public ResponseBody setStatusText(String statusText) {
        this.statusText = statusText;
        return this;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public ResponseBody setResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public List<Cookie> getResponseCookieList() {
        return responseCookieList;
    }

    public ResponseBody setResponseCookieList(List<Cookie> responseCookieList) {
        this.responseCookieList = responseCookieList;
        return this;
    }
}
