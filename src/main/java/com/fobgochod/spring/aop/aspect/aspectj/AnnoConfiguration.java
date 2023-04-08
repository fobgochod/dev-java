package com.fobgochod.spring.aop.aspect.aspectj;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.fobgochod.spring.aop.aspect")
@EnableAspectJAutoProxy
public class AnnoConfiguration {
}
