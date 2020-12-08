package com.annobot.chatbot.model.config;

public class SimpleMessageElement extends ConversationSchemaElement {

    private String possibleMessages;
    private boolean waitForAnswer;

    public String getPossibleMessages() {
        return possibleMessages;
    }

    public void setPossibleMessages(String possibleMessages) {
        this.possibleMessages = possibleMessages;
    }

    public boolean isWaitForAnswer() {
        return waitForAnswer;
    }

    public void setWaitForAnswer(boolean waitForAnswer) {
        this.waitForAnswer = waitForAnswer;
    }
}
