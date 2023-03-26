package com.fobgochod.system.rpc.handler;

import com.fobgochod.system.rpc.protocol.RpcContent;
import com.fobgochod.system.rpc.util.Constants;
import com.fobgochod.system.rpc.util.ObjectUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * NettyHttpServerHandler.java
 *
 * @author fobgochod
 * @date 2021/8/9 16:37
 */
public class ServerNettyHttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // http协议，这个msg是一个啥：完整的http-request
        FullHttpRequest request = (FullHttpRequest) msg;

        // 这个就是consumer 序列化的RpcContent
        ByteBuf content = request.content();
        byte[] data = new byte[content.readableBytes()];
        content.readBytes(data);
        RpcContent rpcContent = ObjectUtils.deserialize(data, RpcContent.class);
        byte[] contentByte = ObjectUtils.doInvoke(rpcContent);

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(contentByte));

        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, contentByte.length);

        String requestId = request.headers().get(Constants.HTTP_REQUEST_ID);
        if (requestId != null) {
            response.headers().set(Constants.HTTP_REQUEST_ID, requestId);
        }

        //http协议，header+body
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client close");
    }
}

