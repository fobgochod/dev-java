package com.fobgochod.system.rpc.handler;

import com.fobgochod.system.rpc.protocol.RpcEntity;
import com.fobgochod.system.rpc.protocol.RpcHeader;
import com.fobgochod.system.rpc.util.ObjectUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ServerRequestHandler.java
 *
 * @author Xiao
 * @date 2021/8/9 16:12
 */
public class ServerCustomHandler extends ChannelInboundHandlerAdapter {

    // provider:
    //思考下解决方法？
    //
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        RpcEntity rpcEntity = (RpcEntity) msg;

        ctx.executor().execute(() -> {
            // ctx.executor().parent().next().execute(() -> {

            byte[] contentByte = ObjectUtils.doInvoke(rpcEntity.getContent());

            RpcHeader resHeader = new RpcHeader();
            resHeader.setRequestId(rpcEntity.getHeader().getRequestId());
            resHeader.setFlag(RpcHeader.Flag.RESPONSE);
            resHeader.setContentLength(contentByte.length);
            byte[] headerByte = ObjectUtils.serialize(resHeader);
            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerByte.length + contentByte.length);

            byteBuf.writeBytes(headerByte);
            byteBuf.writeBytes(contentByte);
            ctx.writeAndFlush(byteBuf);
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client close");
    }
}

