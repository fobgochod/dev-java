package com.fobgochod.system.rpc.transport;

import com.fobgochod.system.rpc.protocol.RpcContent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClientResponseMappingCallback.java
 *
 * @author fobgochod
 * @date 2021/8/9 15:52
 */
public class ClientRequestMapping {

    private static final ConcurrentHashMap<Long, CompletableFuture<Object>> mapping = new ConcurrentHashMap<>();

    public static void addCallBack(long requestId, CompletableFuture<Object> future) {
        mapping.putIfAbsent(requestId, future);
    }

    public static void runCallBack(long requestId, RpcContent msg) {
        CompletableFuture<Object> future = mapping.get(requestId);
        future.complete(msg.getResData());
        removeCallback(requestId);
    }

    private static void removeCallback(long requestId) {
        mapping.remove(requestId);
    }
}

