package pro.tools.http.pojo;

import java.util.Map;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 13:50
 */
public class HttpReceive {
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 异常本体
     */
    private Throwable throwable;
    /**
     * 状态码
     */
    private Integer statusCode;
    /**
     * 状态文本
     */
    private String statusText;
    /**
     * 响应内容
     */
    private String responseBody;
    /**
     * 响应的header列表
     */
    private Map<String, String> responseHeader;

    /**
     * 是否有异常
     */
    private Boolean haveError;
    /**
     * 是否执行完成
     */
    private Boolean isDone;

    public HttpReceive() {
        init();
    }

    private void init() {
        haveError = false;
        isDone = false;
    }

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
}
