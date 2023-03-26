package com.fobgochod.spring.aop.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class CalculatorProxy {

    public static Calculator getProxy(final Calculator calculator) {
        ClassLoader loader = calculator.getClass().getClassLoader();
        Class<?>[] interfaces = calculator.getClass().getInterfaces();
        InvocationHandler h = (proxy, method, args) -> {
            try {
                Object[] params = {2, 3};
                return method.invoke(calculator, params);
            } catch (Exception ignored) {
            }
            return null;
        };
        Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
        return (Calculator) proxy;
    }
}
