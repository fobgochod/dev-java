package com.fobgochod.spring.aop.adapter.annotation.spring;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.lang.NonNull;

public class SpringConfigurationSelector extends AdviceModeImportSelector<EnableSpring> {

    @Override
    @NonNull
    public String[] selectImports(AdviceMode adviceMode) {
        return switch (adviceMode) {
            case PROXY -> new String[]{ProxySpringConfiguration.class.getName()};
            case ASPECTJ -> new String[0];
        };
    }
}
