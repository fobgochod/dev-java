package com.fobgochod.spring.xml.namespace.handler;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import java.time.LocalDate;

@Component
public class CatBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return Cat.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        String breed = element.getAttribute("breed");
        String birthday = element.getAttribute("birthday");

        if (StringUtils.hasText(id)) {
            builder.addPropertyValue("id", id);
        }
        if (StringUtils.hasText(name)) {
            builder.addPropertyValue("name", name);
        }
        if (StringUtils.hasText(breed)) {
            builder.addPropertyValue("breed", breed);
        }
        if (StringUtils.hasText(birthday)) {
            builder.addPropertyValue("birthday", LocalDate.parse(birthday));
        }
    }
}
