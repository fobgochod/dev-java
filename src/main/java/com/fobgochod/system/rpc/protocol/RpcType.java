package com.fobgochod.system.rpc.protocol;

/**
 * 传输协议
 *
 * @author fobgochod
 * @date 2021/8/9 16:20
 */
public enum RpcType {

    /**
     * 自定义的RPC，有状态 根据requestId区分每次请求
     */
    CUSTOM,

    /**
     * 标准Http协议，无状态
     * 现成的Http工具
     * 每请求对应一个连接
     */
    HTTP_ON_TAP,

    /**
     * 标准Http协议，无状态
     * netty框架对Http提供的编解码
     * 每请求对应一个连接
     */
    HTTP_NETTY,

    /**
     * 多次请求复用同一个Http连接
     */
    HTTP_NETTY_MULTIPLEXING
}
