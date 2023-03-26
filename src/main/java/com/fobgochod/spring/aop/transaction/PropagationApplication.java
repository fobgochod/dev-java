package com.fobgochod.spring.aop.transaction;

import com.fobgochod.spring.aop.transaction.config.TxConfiguration;
import com.fobgochod.spring.aop.transaction.service.PropagationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * TransactionApplication.java
 *
 * @author fobgochod
 * @date 2022/4/13 0:20
 */
public class PropagationApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TxConfiguration.class);
        applicationContext.refresh();
        PropagationService propagationService = applicationContext.getBean(PropagationService.class);

        propagationService.required();
    }
}
