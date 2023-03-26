package com.fobgochod.spring.ioc.additional.event.standard;

import com.fobgochod.spring.ioc.additional.event.generic.EntityCreatedEvent;
import com.fobgochod.spring.ioc.additional.event.generic.Person;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService implements ApplicationEventPublisherAware {

    private List<String> blockedList;
    private ApplicationEventPublisher publisher;

    public void setBlockedList(List<String> blockedList) {
        this.blockedList = blockedList;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void sendEmail(String address, String content) {
        if (blockedList.contains(address)) {
            publisher.publishEvent(new BlockedListEvent(this, address, content));
            publisher.publishEvent(new EntityCreatedEvent<>(new Person(address, content)));
            return;
        }
        // send email...
    }
}

