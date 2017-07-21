package pro.tools.http.netty.exception;

import pro.tools.http.netty.pojo.HttpReceive;
import pro.tools.http.netty.pojo.HttpSend;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 15:57
 */
public class HttpException extends Exception {
    private HttpSend httpSend;
    private HttpReceive httpReceive;

    public HttpException(String msg) {
        super(msg);
    }

    public HttpException(Exception e) {
        super(e);
    }

    public HttpException(String msg, Exception e) {
        super(msg, e);
    }

    public HttpException(String msg, HttpSend httpSend, HttpReceive httpReceive) {
        super(msg);
        this.httpSend = httpSend;
        this.httpReceive = httpReceive;
    }

    @Override
    public String getLocalizedMessage() {
        return super.getMessage().concat("\r\nHTTP_SEND:\t" + httpSend.toString().concat("\r\nHTTP_RECEIVE:\t" + httpReceive.toString()));
    }
}
