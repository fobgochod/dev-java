package com.fobgochod.spring.ioc.annotation.instantiation.resolvebeforeinstantiation;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class BeforePojoMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if ("doSomeThing".equals(method.getName())) {
            System.out.println("目标方法执行之前：" + method);
            Object object = methodProxy.invokeSuper(o, objects);
            System.out.println("目标方法执行之后：" + method);
            return object;
        }
        return o;
    }
}
