package com.fobgochod.spring.ioc.additional.event.standard;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BlockedListNotifier implements ApplicationListener<BlockedListEvent> {

    private String notificationAddress;

    public void setNotificationAddress(String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    public void onApplicationEvent(BlockedListEvent event) {
        // notify appropriate parties via notificationAddress...
        System.out.println("你好：" + notificationAddress + "，垃圾邮件拦截通知：");
        System.out.println("收件人：" + event.getAddress());
        System.out.println("邮件内容：" + event.getContent());
        System.out.println();
    }
}

