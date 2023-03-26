package com.fobgochod.spring.ioc.annotation.instantiation.factorymethod;

import org.springframework.stereotype.Component;

@Component
public class FactoryMethodPojoStaticFactory {

    public static FactoryMethodPojo getPojo(int id, String name) {
        FactoryMethodPojo factoryMethodPojo = new FactoryMethodPojo();
        factoryMethodPojo.setId(id);
        factoryMethodPojo.setName(name);
        return factoryMethodPojo;
    }
}
