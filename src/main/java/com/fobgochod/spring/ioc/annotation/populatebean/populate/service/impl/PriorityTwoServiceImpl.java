package com.fobgochod.spring.ioc.annotation.populatebean.populate.service.impl;

import com.fobgochod.spring.ioc.annotation.populatebean.populate.domain.PopulatePojo;
import com.fobgochod.spring.ioc.annotation.populatebean.populate.service.PopulateService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Priority;

@Priority(2)
@Service
public class PriorityTwoServiceImpl implements PopulateService {

    @Override
    public PopulatePojo info(String name) {
        return new PopulatePojo("@" + name + " @Priority(2)");
    }
}
