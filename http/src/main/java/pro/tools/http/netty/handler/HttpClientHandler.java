package pro.tools.http.netty.handler;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.format.ToolFormat;
import pro.tools.http.pojo.HttpReceive;
import pro.tools.http.pojo.HttpSend;

import java.util.Map;

/**
 * @author SeanDragon
 */
public class HttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger log = LoggerFactory.getLogger(HttpClientHandler.class);

    private final HttpSend httpSend;
    private final HttpReceive httpReceive;

    public HttpClientHandler(final HttpSend httpSend, final HttpReceive httpReceive) {
        this.httpSend = httpSend;
        this.httpReceive = httpReceive;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final HttpObject msg) {
        if (msg instanceof FullHttpResponse) {
            final FullHttpResponse response = (FullHttpResponse) msg;

            httpReceive.setStatusCode(response.status().code())
                    .setStatusText(response.status().codeAsText().toString());

            HttpHeaders headers = response.headers();
            if (httpSend.getNeedReceiveHeaders() && !headers.isEmpty()) {

                final Map<String, String> responseHeaderMap = Maps.newHashMapWithExpectedSize(headers.size());

                headers.forEach(one -> {
                    responseHeaderMap.put(one.getKey(), one.getValue());
                });

                httpReceive.setResponseHeader(responseHeaderMap);
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
                if (log.isDebugEnabled()) {
                    log.debug("#HTTP 内容开始{");
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("#HTTP 内容开始{");
                }
            }

            final String responseBody = response.content().toString(httpSend.getCharset());

            httpReceive.setResponseBody(responseBody);

            if (log.isDebugEnabled()) {
                log.debug(responseBody);
            }

            if (log.isDebugEnabled()) {
                log.debug("}EOF#");
            }

            final DecoderResult decoderResult = response.decoderResult();
            if (decoderResult.isFailure()) {
                Throwable cause = decoderResult.cause();
                if (log.isErrorEnabled()) {
                    log.error(ToolFormat.toException(cause), cause);
                }
                httpReceive.setHaveError(true)
                        .setErrMsg(cause.getMessage())
                        .setThrowable(cause);
            } else if (response.status().code() != 200) {
                httpReceive.setHaveError(true)
                        .setErrMsg("本次请求响应码不是200，是" + response.status().code());
            }

            httpReceive.setIsDone(true);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        if (log.isWarnEnabled()) {
            log.warn(cause.getMessage(), cause);
        }

        httpReceive.setIsDone(true)
                .setHaveError(true)
                .setErrMsg(cause.toString())
                .setThrowable(cause);
        ctx.close();
    }
}
