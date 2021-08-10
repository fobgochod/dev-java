package com.fobgochod.system.rpc.handler;

import com.fobgochod.system.rpc.protocol.RpcContent;
import com.fobgochod.system.rpc.protocol.RpcEntity;
import com.fobgochod.system.rpc.protocol.RpcHeader;
import com.fobgochod.system.rpc.util.Constants;
import com.fobgochod.system.rpc.util.ObjectUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 数据包处理
 * 拆包、粘包
 * <p>
 * 父类里一定有channelread{  前老的拼buf  decode（）；剩余留存 ;对out遍历 } -> bytebuf
 * 因为你偷懒，自己能不能实现！
 *
 * @author Xiao
 * @date 2021/8/9 16:10
 */
public class ServerDecoderHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        while (buf.readableBytes() >= Constants.HEADER_LENGTH) {
            byte[] bytes = new byte[Constants.HEADER_LENGTH];
            // 从哪里读取，读多少，但是readerIndex不变
            buf.getBytes(buf.readerIndex(), bytes);
            RpcHeader header = ObjectUtils.deserialize(bytes, RpcHeader.class);

            // decode在2个方向都使用
            // 通信的协议
            if (buf.readableBytes() - Constants.HEADER_LENGTH >= header.getContentLength()) {
                // 移动指针到body开始的位置
                buf.readBytes(Constants.HEADER_LENGTH);
                byte[] data = new byte[header.getContentLength()];
                buf.readBytes(data);
                RpcContent content = ObjectUtils.deserialize(data, RpcContent.class);

                if (header.getFlag() == RpcHeader.Flag.REQUEST) {
                    out.add(new RpcEntity(header, content));

                } else if (header.getFlag() == RpcHeader.Flag.RESPONSE) {
                    out.add(new RpcEntity(header, content));
                }
            } else {
                break;
            }
        }
    }
}

