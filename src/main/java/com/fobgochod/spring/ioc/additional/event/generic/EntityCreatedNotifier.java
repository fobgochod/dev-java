package com.fobgochod.spring.ioc.additional.event.generic;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EntityCreatedNotifier {

    @EventListener
    public void onPersonCreated(EntityCreatedEvent<Person> event) {
        System.out.println("person = " + event.getSource());
    }
}

