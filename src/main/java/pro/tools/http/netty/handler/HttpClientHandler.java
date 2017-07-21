/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package pro.tools.http.netty.handler;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.tools.http.netty.pojo.HttpReceive;
import pro.tools.http.netty.pojo.HttpSend;

import java.util.Map;

public class HttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger log = LoggerFactory.getLogger(HttpClientHandler.class);

    private final HttpSend httpSend;
    private HttpReceive httpReceive;

    public HttpClientHandler(final HttpSend httpSend, HttpReceive httpReceive) {
        this.httpSend = httpSend;
        this.httpReceive = httpReceive;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;

            httpReceive.setStatusCode(response.status().code())
                    .setStatusText(response.status().codeAsText().toString());

            if (!response.headers().isEmpty()) {

                Map<String, String> responseHeaderMap = Maps.newHashMap();

                response.headers().forEach(one -> {
                    responseHeaderMap.put(one.getKey(), one.getValue());
                });

                httpReceive.setResponseHeader(responseHeaderMap);
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
                log.debug("#HTTP 内容开始{");
            } else {
                log.debug("}EOF#");
            }

            String responseBody = response.content().toString(httpSend.getCharset());

            httpReceive.setResponseBody(responseBody);

            log.debug(responseBody);

            log.debug("}EOF#");

            DecoderResult decoderResult = response.decoderResult();
            if (decoderResult.isFailure()) {
                httpReceive.setHaveError(true)
                        .setErrMsg(decoderResult.cause().toString())
                        .setThrowable(decoderResult.cause());
            }

            httpReceive.setIsDone(true);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn(cause.getMessage(), cause);
        httpReceive.setIsDone(true);
        httpReceive.setHaveError(true);
        httpReceive.setErrMsg(cause.toString());
        httpReceive.setThrowable(cause);
        ctx.close();
    }
}
