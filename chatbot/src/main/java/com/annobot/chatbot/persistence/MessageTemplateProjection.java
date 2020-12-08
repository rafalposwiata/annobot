package com.annobot.chatbot.persistence;

import com.google.common.collect.Lists;
import com.annobot.chatbot.model.Message;
import com.annobot.chatbot.model.MessageType;

import java.util.List;
import java.util.Map;

import static com.annobot.chatbot.ChatbotUtils.pickRandomly;
import static java.util.stream.Collectors.toMap;

@Deprecated
public class MessageTemplateProjection {

    private Map<MessageType, List<String>> messages;

    public MessageTemplateProjection(MessageTemplateDao messageTemplateDao) {
        this.messages = messageTemplateDao
                .findAll()
                .stream()
                .collect(toMap(MessageTemplateEntity::getMessageType, m -> Lists.newArrayList(m.getContent()),
                        (v1, v2) -> {
                            v1.addAll(v2);
                            return v1;
                        }));
    }

    public Message get(MessageType messageType) {
        List<String> possibleMessages = messages.get(messageType);
        String message = pickRandomly(possibleMessages);

        return new Message();
    }
}
