package com.annobot.chatbot.model;

import com.annobot.chatbot.validation.MessageValidator;

public class State {

    private MessageType messageType;
    private State mainPath;
    private State alternativePath;
    private MessageValidator messageValidator;
    private boolean goToNextState = false;

    private State(MessageType messageType) {
        this.messageType = messageType;
        this.messageValidator = message -> true;
    }

    public static State create(MessageType messageType) {
        return new State(messageType);
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setMainPath(State mainPath) {
        this.mainPath = mainPath;
    }

    public void setAlternativePath(State alternativePath) {
        this.alternativePath = alternativePath;
    }

    public State setMessageValidator(MessageValidator messageValidator) {
        this.messageValidator = messageValidator;
        return this;
    }

    public State setGoToNextState(boolean goToNextState) {
        this.goToNextState = goToNextState;
        return this;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public State getNextState(Message message) {
        return messageValidator.validate(message) ? mainPath : alternativePath;
    }

    public boolean isGoToNextState() {
        return goToNextState;
    }
}
