package com.fobgochod.system.rpc.handler;

import com.fobgochod.system.rpc.protocol.RpcEntity;
import com.fobgochod.system.rpc.transport.ClientRequestMapping;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端处理响应的结果
 *
 * @author Xiao
 * @date 2021/8/9 16:08
 */
public class ClientCustomHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcEntity rpcEntity = (RpcEntity) msg;
        //曾经我们没考虑返回的事情
        ClientRequestMapping.runCallBack(rpcEntity.getHeader().getRequestId(), rpcEntity.getContent());
    }
}

