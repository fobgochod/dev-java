package com.fobgochod.spring.ioc.annotation.circularreference.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class CycleLogger {

    @Pointcut("execution(public * com.fobgochod.spring.ioc.annotation.circularreference.cycle.*.name(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        //获取方法签名
        Signature signature = joinPoint.getSignature();
        //获取参数信息
        Object[] args = joinPoint.getArgs();
        System.out.println("【" + signature.getName() + "】方法开始执行，参数是：" + Arrays.asList(args));
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("【" + signature.getName() + "】方法执行结束。");
    }

}
