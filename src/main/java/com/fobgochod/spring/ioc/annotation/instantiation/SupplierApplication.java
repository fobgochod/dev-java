package com.fobgochod.spring.ioc.annotation.instantiation;

import com.fobgochod.spring.ioc.annotation.instantiation.supplier.SupplierPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.fobgochod.spring.ioc.annotation.instantiation.supplier")
public class SupplierApplication {

    @Autowired
    private SupplierPojo supplierPojo;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(SupplierApplication.class);
        ac.refresh();

        SupplierApplication app = ac.getBean(SupplierApplication.class);
        System.out.println("supplierPojo = " + app.supplierPojo);
    }
}
