package com.fobgochod.spring.xml.namespace.handler;

import java.time.LocalDate;

public class Cat {

    private String id;
    private String name;
    private String breed;
    private LocalDate birthday;

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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\""
                + id + '\"'
                + ",\"name\":\""
                + name + '\"'
                + ",\"breed\":\""
                + breed + '\"'
                + ",\"birthday\":"
                + birthday
                + "}";
    }
}
