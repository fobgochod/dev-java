package com.fobgochod.spring.aop.adapter.annotation.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ProxySpringConfiguration {

    private static final String SPRING_ANNOTATION_PROCESSOR_BEAN_NAME = "internalSpringAnnotationProcessor";

    @Bean(name = SPRING_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SpringAnnotationBeanPostProcessor springAdvisor() {
        return new SpringAnnotationBeanPostProcessor();
    }
}
