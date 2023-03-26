package com.fobgochod.java.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatRoom2 {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoom2.class);

    public static void main(String[] args) {
        new ChatClient().chatClient();
    }
}
