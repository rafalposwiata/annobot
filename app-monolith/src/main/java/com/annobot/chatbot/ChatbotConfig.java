package com.annobot.chatbot;

import com.annobot.chatbot.integration.DatasetClient;
import com.annobot.chatbot.integration.MLModelFactory;
import com.annobot.chatbot.integration.StatisticsClient;
import com.annobot.chatbot.persistence.*;
import com.annobot.chatbot.validation.Validator;
import com.annobot.chatbot.validation.ValidatorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatbotConfig {

    private final MessageTemplateDao messageTemplateDao;
    private final MessageDao messageDao;

    @Autowired
    public ChatbotConfig(MessageTemplateDao messageTemplateDao, MessageDao messageDao) {
        this.messageTemplateDao = messageTemplateDao;
        this.messageDao = messageDao;
    }

    @Bean
    public MessageRepository messageRepository(MessageDao messageDao) {
        return new MessageRepository(messageDao);
    }

    @Bean
    public MessageTemplateProjection messagesProjection() {
        return new MessageTemplateProjection(messageTemplateDao);
    }

    @Bean
    public MessageFactory messageFactory(DatasetClient datasetClient) {
        return new MessageFactoryImpl(datasetClient);
    }

    @Bean
    public ChatbotService chatbotService(ObjectMapper objectMapper, ConfigDao configDao) {
        return new ChatbotServiceImpl(objectMapper, configDao);
    }

    @Bean
    public Validator validator(){
        return new ValidatorImpl();
    }

    @Bean
    public Chatbot chatbot(MessageFactory messageFactory, MessageRepository messageRepository,
                           StatisticsClient statisticsClient, Validator validator, ChatbotService chatbotService,
                           DatasetClient datasetClient, MLModelFactory mlModelFactory) {
        return new ChatbotImpl(messageFactory, messageRepository, statisticsClient, validator, chatbotService,
                datasetClient, mlModelFactory);
    }
}
