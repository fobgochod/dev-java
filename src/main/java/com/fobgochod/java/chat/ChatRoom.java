package com.fobgochod.java.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatRoom {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoom.class);

    public static void main(String[] args) {
        new ChatClient().chatClient();
    }
}
