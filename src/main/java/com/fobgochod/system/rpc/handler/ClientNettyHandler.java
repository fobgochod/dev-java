package com.fobgochod.system.rpc.handler;

import com.fobgochod.system.rpc.protocol.RpcContent;
import com.fobgochod.system.rpc.util.ObjectUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.concurrent.CompletableFuture;

/**
 *  ClientNettyHandler.java
 *
 * @author fobgochod
 * @date 2021/8/9 22:46
 */
public class ClientNettyHandler extends ChannelInboundHandlerAdapter {

    CompletableFuture<Object> future;

    public ClientNettyHandler(CompletableFuture<Object> future) {
        this.future = future;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //3，接收   预埋的回调，根据netty对socket io 事件的响应
        //客户端的msg是啥：完整的http-response
        FullHttpResponse response = (FullHttpResponse) msg;

        ByteBuf resContent = response.content();
        byte[] data = new byte[resContent.readableBytes()];
        resContent.readBytes(data);

        RpcContent o = ObjectUtils.deserialize(data, RpcContent.class);

        future.complete(o.getResData());
    }
}
