package com.annobot.chatbot.integration;

public interface StatisticsClient {

    void send(String userName, String itemId, String description, String timeDiff, boolean mobile);
}
