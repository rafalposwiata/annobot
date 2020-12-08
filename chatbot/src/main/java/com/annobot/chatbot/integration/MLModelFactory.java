package com.annobot.chatbot.integration;

public interface MLModelFactory {

    MLModelClient create(String mlModelUrl);
}
