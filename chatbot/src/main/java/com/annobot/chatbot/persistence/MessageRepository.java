package com.annobot.chatbot.persistence;

import com.annobot.chatbot.model.Message;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public class MessageRepository {

    private final MessageDao messageDao;

    public MessageRepository(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Transactional
    public void save(Message message) {
        MessageEntity messageEntity = new MessageEntity(message, LocalDateTime.now());
        messageDao.save(messageEntity);
    }
}
