package com.annobot.chatbot;

import com.annobot.chatbot.model.Message;

import java.util.List;

public interface Chatbot {

    List<Message> init(String botName, String user, boolean mobile);

    List<Message> receiveMessage(Message message);

    boolean conversationIsActive(String botName, String user);
}
