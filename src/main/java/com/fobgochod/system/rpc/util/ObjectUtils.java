package com.fobgochod.system.rpc.util;

import com.fobgochod.system.rpc.dispatcher.Dispatcher;
import com.fobgochod.system.rpc.protocol.RpcContent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * ObjectUtils.java
 *
 * @author Xiao
 * @date 2021/8/9 15:37
 */
public class ObjectUtils {

    static ByteArrayOutputStream out = new ByteArrayOutputStream();

    /**
     * 序列化
     *
     * @param msg 具体对象
     */
    public synchronized static byte[] serialize(Object msg) {
        out.reset();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(msg);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化
     *
     * @param bytes     字节数组
     * @param valueType 目标对象类型
     */
    public synchronized static <T> T deserialize(byte[] bytes, Class<T> valueType) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(in);
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行具体服务，返回结果，并包装成字节码
     */
    public static byte[] doInvoke(RpcContent rpcContent) {

        String serviceName = rpcContent.getName();
        String method = rpcContent.getMethodName();
        Object service = Dispatcher.getDispatcher().get(serviceName);
        Class<?> clazz = service.getClass();
        Object result = null;
        try {
            Method m = clazz.getMethod(method, rpcContent.getParameterTypes());
            result = m.invoke(service, rpcContent.getArgs());
        } catch (Exception e) {
            e.printStackTrace();
        }

        RpcContent resContent = new RpcContent();
        resContent.setResData(result);
        return ObjectUtils.serialize(resContent);
    }
}
