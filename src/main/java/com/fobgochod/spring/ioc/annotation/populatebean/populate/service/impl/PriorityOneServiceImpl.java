package com.fobgochod.spring.ioc.annotation.populatebean.populate.service.impl;

import com.fobgochod.spring.ioc.annotation.populatebean.populate.domain.PopulatePojo;
import com.fobgochod.spring.ioc.annotation.populatebean.populate.service.PopulateService;
import jakarta.annotation.Priority;
import org.springframework.stereotype.Service;

@Priority(1)
@Service
public class PriorityOneServiceImpl implements PopulateService {

    @Override
    public PopulatePojo info(String name) {
        return new PopulatePojo("@" + name + " @Priority(1)");
    }
}
