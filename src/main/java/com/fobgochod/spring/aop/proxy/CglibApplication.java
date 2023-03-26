package com.fobgochod.spring.aop.proxy;

import com.fobgochod.spring.aop.proxy.cglib.Calcuator;
import com.fobgochod.spring.aop.proxy.cglib.CglibMethodInterceptor;
import com.fobgochod.spring.CGlibUtils;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Cglib动态代理
 *
 * @author seven
 * @date 2021/4/29
 */
public class CglibApplication {

    public static void main(String[] args) throws Exception{
        CGlibUtils.generatedCGlibProxyFiles("cglib");
        // 通过Cglib动态代理获取代理对象的过程，创建调用对象
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(Calcuator.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new CglibMethodInterceptor());
        // 创建代理对象
        Calcuator calculator = (Calcuator) enhancer.create();
        // 通过代理对象调用目标方法
        int add = calculator.add(1, 2);
        System.out.println("add = " + add);
        System.out.println("calculator = " + calculator.getClass());
    }
}
