package com.fobgochod.spring.aop.adapter.service;

import com.fobgochod.spring.aop.adapter.annotation.summer.Summer;
import com.fobgochod.spring.aop.aspect.util.AopLogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CalculatorAspect {

    @Pointcut("execution(public int com.fobgochod.spring.aop.adapter.service.JdkCalculator.add(int, int))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        Object[] args = pjp.getArgs();
        Object result = null;
        try {
            AopLogUtil.annotationLog(0, Around.class, signature.getName(), pjp.getArgs(), null);
            result = pjp.proceed(args);
            AopLogUtil.annotationLog(6, Around.class, signature.getName(), args, result);
        } finally {
            AopLogUtil.annotationLog(7, Around.class, signature.getName(), args, result);
        }
        return result;
    }

    @Before(value = "pointCut()")
    private void beforeMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        AopLogUtil.annotationLog(1, Before.class, signature.getName(), args, null);
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        AopLogUtil.annotationLog(4, AfterReturning.class, signature.getName(), new Object[]{}, result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception e) {
        Signature signature = joinPoint.getSignature();
        AopLogUtil.annotationLog(4, AfterThrowing.class, signature.getName(), new Object[]{}, e.getMessage());
    }

    @After("pointCut()")
    public void afterMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        AopLogUtil.annotationLog(5, After.class, signature.getName(), joinPoint.getArgs(), null);
    }

    @Summer("pointCut()")
    public void summerMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        AopLogUtil.annotationLog(2, Summer.class, signature.getName(), joinPoint.getArgs(), null);
    }
}
