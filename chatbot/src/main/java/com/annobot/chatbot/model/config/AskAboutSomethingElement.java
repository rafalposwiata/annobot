package com.annobot.chatbot.model.config;

public class AskAboutSomethingElement extends SimpleMessageElement {

    private String validator;
    private String validationMessage;

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }
}
