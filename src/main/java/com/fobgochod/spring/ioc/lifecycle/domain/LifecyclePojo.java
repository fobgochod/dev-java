package com.fobgochod.spring.ioc.lifecycle.domain;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class LifecyclePojo implements InitializingBean, DisposableBean {

    @PostConstruct
    public void postConstruct() {
        System.out.println("1. initialization methods by @PostConstruct.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("2. initialization methods by InitializingBean.");
    }

    public void init() {
        System.out.println("3. initialization methods by custom configured init().");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("4. destroy methods by @PreDestroy.");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("5. destroy methods by DisposableBean.");
    }

    public void cleanup() {
        System.out.println("6. destroy methods by custom configured destroy().");
    }
}
