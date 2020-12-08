package com.annobot.externalclients.integration;

import com.annobot.externalclients.facebook.model.Event;
import com.annobot.externalclients.facebook.model.Message;
import com.annobot.externalclients.integration.model.FacebookTokens;

import java.util.List;

public interface ChatbotClient {

    FacebookTokens getFacebookTokens(String botName);

    List<Message> sendToBot(String botName, Event event);
}
