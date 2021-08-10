package com.fobgochod.system.rpc.protocol;

import java.io.Serializable;
import java.util.UUID;

/**
 * 通信上的协议
 * 1.protocol: 32bit可以设置很多信息
 * 客户端发送到服务端: 0x14141414  0x14->0001 0100
 * 服务端发送到客户端: 0x14141424
 * 2.contentLength: 数据长度
 * 3.UUID: requestId
 *
 * @author Xiao
 * @date 2021/8/9 15:28
 */
public class RpcHeader implements Serializable {

    public interface Flag {
        int REQUEST = 0x14141414;
        int RESPONSE = 0x14141424;
    }

    private int flag;
    private int contentLength;
    private long requestId;


    public static RpcHeader requestHeader(byte[] msg) {
        RpcHeader header = new RpcHeader();
        int size = msg.length;
        long requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());

        header.setFlag(Flag.REQUEST);
        header.setContentLength(size);
        header.setRequestId(requestId);
        return header;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}
