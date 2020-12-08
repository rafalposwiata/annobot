package com.annobot.chatbot.integration;

public interface MLModelClient {

    String predict(String modelName, String text);
}
