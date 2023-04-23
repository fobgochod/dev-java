package com.fobgochod.spring.aop.adapter.annotation.spring;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import java.lang.annotation.Annotation;

public class SpringAnnotationAdvisor extends AbstractPointcutAdvisor {

    private final Advice advice;
    private Pointcut pointcut;

    public SpringAnnotationAdvisor(Class<? extends Annotation> springAnnotationType) {
        this.advice = buildAdvice();
        this.pointcut = buildPointcut(springAnnotationType);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    protected Advice buildAdvice() {
        return new SpringInterceptor();
    }

    protected Pointcut buildPointcut(Class<? extends Annotation> springAnnotationType) {
        Pointcut cpc = new AnnotationMatchingPointcut(springAnnotationType, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(springAnnotationType);
        return new ComposablePointcut(cpc).union(mpc);
    }
}
