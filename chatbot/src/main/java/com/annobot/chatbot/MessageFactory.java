package com.annobot.chatbot;

import com.annobot.chatbot.model.Message;

public interface MessageFactory {

    Message createMessage(String botName, String conversationId, Long datasetId, Integer idx);

    Message createMessage(String botName, String conversationId, String messages);
}
