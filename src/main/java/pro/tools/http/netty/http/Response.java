package pro.tools.http.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultHttpResponse;

/**

 */
public class Response {
    private DefaultHttpResponse response;
    private ByteBuf body;

    public Response(DefaultHttpResponse response, ByteBuf body) {
        this.response = response;
        this.body = body;
    }


    public ByteBuf getBody() {
        return body;
    }

    public void setBody(ByteBuf body) {
        this.body = body;
    }

    public DefaultHttpResponse getResponse() {
        return response;
    }

    public void setResponse(DefaultHttpResponse response) {
        this.response = response;
    }
}
