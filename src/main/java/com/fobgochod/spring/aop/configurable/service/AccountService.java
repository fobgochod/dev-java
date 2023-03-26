package com.fobgochod.spring.aop.configurable.service;

import org.springframework.stereotype.Component;

@Component
public class AccountService {

    private String name = "I'm spring ioc container managed bean.";

    public String getName() {
        return name;
    }
}
