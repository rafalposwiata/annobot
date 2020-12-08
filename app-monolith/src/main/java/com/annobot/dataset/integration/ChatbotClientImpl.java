package com.annobot.dataset.integration;

import com.annobot.chatbot.persistence.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("DatasetChatbotClient")
public class ChatbotClientImpl implements ChatbotClient {

    private MessageDao messageDao;

    @Autowired
    public ChatbotClientImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public List<String> annotationForUser(String userId) {
        List<String> result = messageDao.getAnnotationByUserId(userId).stream().map(e -> {
            String content = e.getContent();
            if (content.equals("a"))
                return "Positive";
            if (content.equals("s"))
                return "Negative";
            return content;
        }).collect(Collectors.toList());
        return result;
    }
}
