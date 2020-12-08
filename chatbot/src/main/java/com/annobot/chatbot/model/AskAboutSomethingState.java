package com.annobot.chatbot.model;

import com.annobot.chatbot.MessageFactory;
import com.annobot.chatbot.model.ConversationState;
import com.annobot.chatbot.model.config.AskAboutSomethingElement;
import com.annobot.chatbot.validation.Validator;
import com.google.common.collect.Lists;

import java.util.List;

public class AskAboutSomethingState extends ConversationState<AskAboutSomethingElement> {

    private Validator validator;

    public AskAboutSomethingState(AskAboutSomethingElement element, Validator validator) {
        super(element);
        this.validator = validator;
    }

    @Override
    public void receive(Message message) {
        goToNext = validator.validate(message, element.getValidator());
    }

    @Override
    public List<Message> getMessages(String botName, String conversationId, MessageFactory messageFactory) {
        Message message;
        if (firstMessageWasSend) {
            message = messageFactory.createMessage(botName, conversationId, element.getValidationMessage());
        } else {
            message = messageFactory.createMessage(botName, conversationId, element.getPossibleMessages());
            firstMessageWasSend = true;
        }
        message = addMessageType(message);
        return Lists.newArrayList(message);
    }
}
