package com.annobot.chatbot.model;

import com.annobot.chatbot.MessageFactory;
import com.annobot.chatbot.model.config.ConversationSchemaElement;

import java.util.List;

public abstract class ConversationState<T extends ConversationSchemaElement> {

    protected T element;
    protected boolean firstMessageWasSend = false;
    protected boolean goToNext = false;

    public ConversationState(T element) {
        this.element = element;
    }

    public abstract void receive(Message message);

    public abstract List<Message> getMessages(String botName, String conversationId, MessageFactory messageFactory);

    public boolean goToNext() {
        return goToNext;
    }

    protected Message addMessageType(Message message) {
        if (message != null)
            message.setMessageType(element.getName() + " - " + element.getType().name());
        return message;
    }
}
