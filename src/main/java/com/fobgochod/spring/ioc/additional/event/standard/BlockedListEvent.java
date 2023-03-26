package com.fobgochod.spring.ioc.additional.event.standard;

import org.springframework.context.ApplicationEvent;

public class BlockedListEvent extends ApplicationEvent {

    private final String address;
    private final String content;

    public BlockedListEvent(Object source, String address, String content) {
        super(source);
        this.address = address;
        this.content = content;
    }

    // accessor and other methods...

    public String getAddress() {
        return address;
    }

    public String getContent() {
        return content;
    }
}

