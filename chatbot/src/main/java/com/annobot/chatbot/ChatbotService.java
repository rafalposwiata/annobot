package com.annobot.chatbot;

import com.annobot.chatbot.model.DeleteBotRequest;
import com.annobot.chatbot.model.config.ChatbotConfiguration;

import java.util.List;

public interface ChatbotService {

    String save(ChatbotConfiguration chatbotConfiguration);

    String delete(DeleteBotRequest deleteBotRequest);

    ChatbotConfiguration getConfig(String botName);

    List<String> getChatbots();
}
