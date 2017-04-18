package pro.tools.http;

import com.google.common.base.MoreObjects;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;
import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * http响应实体
 *
 * @author SeanDragon
 *         Create By 2017-04-07 9:06
 */
public final class ToolHttpReceive {

    private boolean haveError;
    private String errMsg;
    private int statusCode;
    private String statusText;
    private String responseBody;
    private List<Cookie> responseCookieList;
    private Response response;

    public boolean isHaveError() {
        return haveError;
    }

    public ToolHttpReceive setHaveError(boolean haveError) {
        this.haveError = haveError;
        return this;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public ToolHttpReceive setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ToolHttpReceive setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusText() {
        return statusText;
    }

    public ToolHttpReceive setStatusText(String statusText) {
        this.statusText = statusText;
        return this;
    }

    @Nullable
    public String getResponseBody() {
        return responseBody;
    }

    public ToolHttpReceive setResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public List<Cookie> getResponseCookieList() {
        return responseCookieList;
    }

    public ToolHttpReceive setResponseCookieList(List<Cookie> responseCookieList) {
        this.responseCookieList = responseCookieList;
        return this;
    }

    @Nullable
    public Response getResponse() {
        return response;
    }

    public ToolHttpReceive setResponse(Response response) {
        this.response = response;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("haveError", haveError)
                .add("errMsg", errMsg)
                .add("statusCode", statusCode)
                .add("statusText", statusText)
                .add("responseBody", responseBody)
                .add("responseCookieList", responseCookieList)
                .add("response", response)
                .toString();
    }
}
