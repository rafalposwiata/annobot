package com.annobot.mlmodelintegration.model;

public class PredictionRequest {

    private String text;

    public PredictionRequest() {
    }

    public PredictionRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
