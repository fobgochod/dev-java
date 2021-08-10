package com.fobgochod.system.rpc.proxy;

import com.fobgochod.system.rpc.dispatcher.Dispatcher;
import com.fobgochod.system.rpc.protocol.RpcType;
import com.fobgochod.system.rpc.protocol.RpcContent;
import com.fobgochod.system.rpc.transport.ClientFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;

/**
 * ProxyFactory.java
 *
 * @author Xiao
 * @date 2021/8/9 15:42
 */
public class ProxyFactory {

    public static <T> T getProxy(Class<T> intf, RpcType rpcType) {

        ClassLoader loader = intf.getClassLoader();
        Class<?>[] methodInfo = {intf};

        // LOCAL REMOTE 实现: 用到dispatcher  直接给你返回，还是本地调用的时候也代理一下
        return (T) Proxy.newProxyInstance(loader, methodInfo, (proxy, method, args) -> {

            Object obj = Dispatcher.getDispatcher().get(intf.getName());
            // TODO 应该在service的方法执行的时候确定是本地的还是远程的，用到dispatcher来区分下
            if (obj != null) {
                // 走本地 local
                // 为什么走代理？插入一些插件的机会，做一些扩展
                System.out.println("local function call...");
                Class<?> clazz = obj.getClass();
                Method m = clazz.getMethod(method.getName(), method.getParameterTypes());
                return m.invoke(obj, args);
            } else {
                // 走 RPC
                String name = intf.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();

                // RPC 就像小火车拉货 content是service的具体数据，但是还需要header层完成IO传输的控制
                RpcContent content = new RpcContent();
                content.setArgs(args);
                content.setName(name);
                content.setMethodName(methodName);
                content.setParameterTypes(parameterTypes);

                /**
                 * TODO 未来的小火车可能会变
                 *
                 * 1.缺失了注册发现，zk
                 * 2.第一层负载面向的Provider
                 * 3.Consumer 线程池  面向service；并发就有木桶，倾斜
                 * serviceA
                 *   ipA:port
                 *      socket1
                 *      socket2
                 *   ipB:port
                 */
                CompletableFuture future = ClientFactory.transport(rpcType, content);
                //阻塞的
                return future.get();
            }
        });
    }
}
