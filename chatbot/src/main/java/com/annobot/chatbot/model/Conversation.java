package com.annobot.chatbot.model;

import com.annobot.chatbot.MessageFactory;
import com.annobot.chatbot.integration.MLModelClient;
import com.annobot.chatbot.model.config.*;
import com.annobot.chatbot.validation.Validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.annobot.chatbot.model.config.ConversationSchemaElementType.*;

public class Conversation {

    private String conversationId;
    private String botName;
    private Message lastMessage;
    private Date lastMessageDate;
    private Message lastUserMessage;
    private Integer elementId = 0;

    private List<ConversationState> states;
    private MessageFactory messageFactory;

    public Conversation(String conversationId, String botName, List<? extends ConversationSchemaElement> schema,
                        MessageFactory messageFactory, Validator validator, MLModelClient modelClient) {
        this.conversationId = conversationId;
        this.botName = botName;
        this.messageFactory = messageFactory;
        this.states = schema.stream().map(element -> {
            if (SIMPLE_MESSAGE.equals(element.getType())) {
                return new SimpleMessageState((SimpleMessageElement) element);
            } else if (ASK_ABOUT_SOMETHING.equals(element.getType())) {
                return new AskAboutSomethingState((AskAboutSomethingElement) element, validator);
            } else if (DATASET.equals(element.getType())) {
                return new DatasetState((DatasetElement) element);
            } else if (MODEL_PREDICTION.equals(element.getType())) {
                return new ModelPredictionState((ModelPredictionElement) element, modelClient);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void receive(Message message) {
        message.setUserMessage(true);
        message.setMessageType("User response");
        message.setBotName(botName);
        getElement().receive(message);
        lastUserMessage = message;
    }

    public List<Message> getMessages() {
        List<Message> result = new ArrayList<>();
        do {
            ConversationState element = getElement();
            if (element.goToNext()) {
                incrementElementId();
                element = getElement();
            }
            result.addAll(element.getMessages(botName, conversationId, messageFactory));
        } while (responseNotFinished());
        return result;
    }

    private ConversationState getElement() {
        return states.get(elementId);
    }

    private void incrementElementId() {
        if (elementId + 1 == states.size())
            return;
        elementId++;
    }

    private boolean responseNotFinished() {
        return (elementId + 1 < states.size()) && getElement().goToNext();
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessageDate = new Date();
        this.lastMessage = lastMessage;
    }
}
