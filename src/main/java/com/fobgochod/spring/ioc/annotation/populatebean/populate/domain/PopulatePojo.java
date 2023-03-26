package com.fobgochod.spring.ioc.annotation.populatebean.populate.domain;

/**
 * Pojo.java
 *
 * @author fobgochod
 * @date 2022/4/9 13:26
 */
public class PopulatePojo {

    private Integer id = System.identityHashCode(this);
    private String name;

    public PopulatePojo() {
    }

    public PopulatePojo(String name) {
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
        return "Pojo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
