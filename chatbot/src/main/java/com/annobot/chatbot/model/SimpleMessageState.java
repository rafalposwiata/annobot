package com.annobot.chatbot.model;

import com.annobot.chatbot.MessageFactory;
import com.annobot.chatbot.model.config.SimpleMessageElement;
import com.google.common.collect.Lists;

import java.util.List;

public class SimpleMessageState extends ConversationState<SimpleMessageElement> {

    public SimpleMessageState(SimpleMessageElement element) {
        super(element);
    }

    @Override
    public void receive(Message message) {
        goToNext = true;
    }

    @Override
    public List<Message> getMessages(String botName, String conversationId, MessageFactory messageFactory) {
        Message message = messageFactory.createMessage(botName, conversationId, element.getPossibleMessages());
        message = addMessageType(message);
        firstMessageWasSend = true;
        goToNext = !element.isWaitForAnswer();

        return Lists.newArrayList(message);
    }
}
