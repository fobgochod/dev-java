package com.fobgochod.spring.ioc.annotation.instantiation.supplier;

import org.springframework.stereotype.Component;

@Component
public class SupplierPojo {

    private Integer id = System.identityHashCode(this);
    private String name;

    public SupplierPojo() {
    }

    public SupplierPojo(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pojo{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}
