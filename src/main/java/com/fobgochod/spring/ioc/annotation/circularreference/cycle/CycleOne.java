package com.fobgochod.spring.ioc.annotation.circularreference.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CycleOne {

    @Autowired
    private CycleTwo cycleTwo;

    public CycleTwo getCycleTwo() {
        return cycleTwo;
    }

    public String name() {
        return "I'm CycleOne";
    }
}
