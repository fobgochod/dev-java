package com.fobgochod.spring.xml.namespace.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.stereotype.Component;

@Component
public class AnimeNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("cat", new CatBeanDefinitionParser());
    }
}
