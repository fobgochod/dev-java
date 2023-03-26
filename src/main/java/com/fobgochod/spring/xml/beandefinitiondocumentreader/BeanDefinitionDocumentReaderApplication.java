package com.fobgochod.spring.xml.beandefinitiondocumentreader;

import org.springframework.context.support.AbstractApplicationContext;

public class BeanDefinitionDocumentReaderApplication {

    public static void main(String[] args) {
        AbstractApplicationContext applicationContext = new MyClassPathXmlApplicationContext("classpath:config/animal.xml");
        applicationContext.close();
    }
}
