package com.fobgochod.system.rpc.handler;

import com.fobgochod.system.rpc.protocol.RpcContent;
import com.fobgochod.system.rpc.transport.ClientRequestMapping;
import com.fobgochod.system.rpc.util.Constants;
import com.fobgochod.system.rpc.util.ObjectUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * ClientNettyMultiplexingHandler.java
 *
 * @author fobgochod
 * @date 2021/8/9 22:46
 */
public class ClientNettyMultiplexingHandler extends ChannelInboundHandlerAdapter {

    public ClientNettyMultiplexingHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //3，接收   预埋的回调，根据netty对socket io 事件的响应
        //客户端的msg是啥：完整的http-response
        FullHttpResponse response = (FullHttpResponse) msg;

        ByteBuf resContent = response.content();
        byte[] data = new byte[resContent.readableBytes()];
        resContent.readBytes(data);

        RpcContent rpcContent = ObjectUtils.deserialize(data, RpcContent.class);

        String requestId = response.headers().get(Constants.HTTP_REQUEST_ID);
        ClientRequestMapping.runCallBack(Long.parseLong(requestId), rpcContent);
    }
}
