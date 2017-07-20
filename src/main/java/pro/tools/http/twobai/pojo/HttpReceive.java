package pro.tools.http.twobai.pojo;

import com.google.common.base.MoreObjects;

import java.util.Map;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 13:50
 */
public class HttpReceive implements java.io.Serializable {
    private Boolean haveError;
    private String errMsg;
    private Throwable throwable;
    private Integer statusCode;
    private String statusText;
    private String responseBody;
    private Map<String, String> responseCookie;
    private Map<String, String> responseHeader;

    private Boolean isDone;

    public HttpReceive() {
        init();
    }

    private void init() {
        haveError = false;
        isDone = false;
    }

    //region GetterSetter
    public Boolean getHaveError() {
        return haveError;
    }

    public HttpReceive setHaveError(Boolean haveError) {
        this.haveError = haveError;
        return this;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public HttpReceive setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpReceive setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public HttpReceive setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusText() {
        return statusText;
    }

    public HttpReceive setStatusText(String statusText) {
        this.statusText = statusText;
        return this;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public HttpReceive setResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public Map<String, String> getResponseCookie() {
        return responseCookie;
    }

    public HttpReceive setResponseCookie(Map<String, String> responseCookie) {
        this.responseCookie = responseCookie;
        return this;
    }

    public Map<String, String> getResponseHeader() {
        return responseHeader;
    }

    public HttpReceive setResponseHeader(Map<String, String> responseHeader) {
        this.responseHeader = responseHeader;
        return this;
    }

    public Boolean getIsDone() {
        return this.isDone;
    }

    public HttpReceive setIsDone(Boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    //endregion


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("haveError", haveError)
                .add("errMsg", errMsg)
                .add("throwable", throwable)
                .add("statusCode", statusCode)
                .add("statusText", statusText)
                .add("responseBody", responseBody)
                .add("responseCookie", responseCookie)
                .add("responseHeader", responseHeader)
                .add("isDone", isDone)
                .toString();
    }
}
