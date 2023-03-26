package com.fobgochod.spring.ioc.environment.placeholder;

import com.fobgochod.spring.manipulation.propertyeditor.editor.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/customer.properties")
public class Customer {

    @Value("${customer.id}")
    private String id;
    @Value("${customer.name}")
    private String name;
    @Value("${customer.address}")
    private Address address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\""
                + id + '\"'
                + ",\"name\":\""
                + name + '\"'
                + ",\"address\":"
                + address
                + "}";
    }
}
