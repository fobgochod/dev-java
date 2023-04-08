package com.fobgochod.spring.aop.aspect.schema;

import com.fobgochod.spring.aop.aspect.util.AopLogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;

/**
 * 通过XML配置实现AOP
 * 排序规则：{@link AspectJAwareAdvisorAutoProxyCreator#DEFAULT_PRECEDENCE_COMPARATOR}
 *
 * @author fobgochod
 * @date 2022/10/5 12:36
 * @see AspectJAwareAdvisorAutoProxyCreator
 */
public class XmlAspect {

    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        Object[] args = pjp.getArgs();
        Object result = null;
        try {
            AopLogUtil.aopXmlLog(1, Around.class, signature.getName(), pjp.getArgs(), null);
            //通过反射的方式调用目标的方法，相当于执行method.invoke(),可以自己修改结果值
            result = pjp.proceed(args);

            AopLogUtil.aopXmlLog(4, Around.class, signature.getName(), args, result);
        } finally {
            AopLogUtil.aopXmlLog(5, Around.class, signature.getName(), args, result);
        }
        return result;
    }

    private int beforeMethod(JoinPoint joinPoint) {
        //获取方法签名
        Signature signature = joinPoint.getSignature();
        //获取参数信息
        Object[] args = joinPoint.getArgs();

        AopLogUtil.aopXmlLog(2, Before.class, signature.getName(), args, null);
        return 100;
    }

    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        AopLogUtil.aopXmlLog(7, AfterReturning.class, signature.getName(), new Object[]{}, result);
    }

    public void afterThrowingMethod(JoinPoint joinPoint, Exception e) {
        Signature signature = joinPoint.getSignature();
        AopLogUtil.aopXmlLog(7, AfterThrowing.class, signature.getName(), new Object[]{}, e.getMessage());
    }

    public void afterMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        AopLogUtil.aopXmlLog(6, After.class, signature.getName(), joinPoint.getArgs(), null);
    }
}
