package com.fobgochod.system.rpc.protocol;

/**
 *  RpcEntity.java
 *
 * @author Xiao
 * @date 2021/8/9 15:30
 */
public class RpcEntity {

    RpcHeader header;
    RpcContent content;

    public RpcHeader getHeader() {
        return header;
    }

    public void setHeader(RpcHeader header) {
        this.header = header;
    }

    public RpcContent getContent() {
        return content;
    }

    public void setContent(RpcContent content) {
        this.content = content;
    }

    public RpcEntity(RpcHeader header, RpcContent content) {
        this.header = header;
        this.content = content;
    }
}
