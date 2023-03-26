package com.fobgochod.spring.ioc.additional.event.annotation;

import com.fobgochod.spring.ioc.additional.event.standard.BlockedListEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnnotationBlockedListNotifier {

    private String notificationAddress;

    public void setNotificationAddress(String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    @EventListener
    public void processBlockedListEvent(BlockedListEvent event) {
        // notify appropriate parties via notificationAddress...
        System.out.println("你好：" + notificationAddress + "，垃圾邮件拦截通知：");
        System.out.println("收件人：" + event.getAddress());
        System.out.println("邮件内容：" + event.getContent());
        System.out.println();
    }
}

