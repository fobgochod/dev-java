package com.fobgochod.spring.aop.adapter.annotation.spring;

import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

public class SpringAnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {

    private Class<? extends Annotation> springAnnotationType = Spring.class;

    public SpringAnnotationBeanPostProcessor() {
        setBeforeExistingAdvisors(true);
    }

    public void setRepositoryAnnotationType(Class<? extends Annotation> springAnnotationType) {
        Assert.notNull(springAnnotationType, "'springAnnotationType' must not be null");
        this.springAnnotationType = springAnnotationType;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.advisor = new SpringAnnotationAdvisor(this.springAnnotationType);
    }
}
