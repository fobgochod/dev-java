package com.fobgochod.spring.ioc.annotation.circularreference.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CycleTwo {

    @Autowired
    private CycleOne cycleOne;

    public CycleOne getCycleOne() {
        return cycleOne;
    }

    public String name() {
        return "I'm CycleTwo";
    }
}
