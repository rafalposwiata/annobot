package com.annobot.chatbot;

import com.annobot.chatbot.model.DeleteBotRequest;
import com.annobot.chatbot.model.config.ChatbotConfiguration;
import com.annobot.chatbot.persistence.ConfigDao;
import com.annobot.chatbot.persistence.ConfigEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

public class ChatbotServiceImpl implements ChatbotService {

    private final ObjectMapper objectMapper;
    private final ConfigDao configDao;

    public ChatbotServiceImpl(ObjectMapper objectMapper, ConfigDao configDao) {
        this.objectMapper = objectMapper;
        this.configDao = configDao;
    }

    @Override
    @Transactional
    public String save(ChatbotConfiguration chatbotConfiguration) {
        try {
            String name = chatbotConfiguration.getName();
            String config = objectMapper.writeValueAsString(chatbotConfiguration);

            ConfigEntity entity = configDao.findByBotName(name);
            if (entity == null)
                entity = new ConfigEntity(name);
            entity.setConfiguration(config);

            configDao.save(entity);
            return name;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public String delete(DeleteBotRequest deleteBotRequest) {
        ConfigEntity entity = configDao.findByBotName(deleteBotRequest.getBotName());
        if (entity != null) {
            configDao.delete(entity);
        }
        return "OK";
    }

    @Override
    public ChatbotConfiguration getConfig(String botName) {
        try {
            ConfigEntity entity = configDao.findByBotName(botName);
            return objectMapper.readValue(entity.getConfiguration(),
                    ChatbotConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getChatbots() {
        return configDao.getBotNames();
    }
}
