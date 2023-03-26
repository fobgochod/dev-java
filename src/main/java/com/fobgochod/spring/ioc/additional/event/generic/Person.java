package com.fobgochod.spring.ioc.additional.event.generic;

public class Person {

    private String address;
    private String content;

    public Person(String address, String content) {
        this.address = address;
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "{"
                + "\"address\":\""
                + address + '\"'
                + ",\"content\":\""
                + content + '\"'
                + "}";
    }
}
