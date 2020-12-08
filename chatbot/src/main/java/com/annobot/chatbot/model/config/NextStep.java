package com.annobot.chatbot.model.config;

public class NextStep {

    private NextStepCondition condition;
    private String elementName;
    private int n;

    public NextStepCondition getCondition() {
        return condition;
    }

    public void setCondition(NextStepCondition condition) {
        this.condition = condition;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
