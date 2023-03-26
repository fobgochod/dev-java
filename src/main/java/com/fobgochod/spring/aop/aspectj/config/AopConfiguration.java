package com.fobgochod.spring.aop.aspectj.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.fobgochod.spring.aop")
@EnableAspectJAutoProxy
public class AopConfiguration {
}
