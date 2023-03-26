package com.fobgochod.spring.aop.configurable;

import com.fobgochod.spring.aop.configurable.domain.Account;
import com.fobgochod.spring.aop.configurable.service.AccountService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@ComponentScan("com.fobgochod.spring.aop.configurable")
@EnableLoadTimeWeaving
@EnableSpringConfigured
public class ConfigurableApplication {

    /**
     * JVM options: -javaagent:/path/to/spring-instrument.jar
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(ConfigurableApplication.class);
        ac.refresh();

        AccountService bean = ac.getBean(AccountService.class);
        System.out.println(bean.getName());

        Account account1 = new Account();
        System.out.println("account1 id = " + account1.getId() + ", service name = " + account1.getAccountService().getName());

        Account account2 = new Account();
        System.out.println("account2 id = " + account2.getId() + ", service name = " + account2.getAccountService().getName());
    }
}
