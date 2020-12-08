package com.annobot.chatbot;

import com.annobot.chatbot.integration.DatasetClient;
import com.annobot.chatbot.model.Message;
import com.annobot.chatbot.model.Record;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.annobot.chatbot.ChatbotUtils.pickRandomly;

public class MessageFactoryImpl implements MessageFactory {

    private DatasetClient datasetClient;

    private Map<String, List<String>> emojis = new HashMap<>();

    public MessageFactoryImpl(DatasetClient datasetClient) {
        this.datasetClient = datasetClient;

        this.emojis.put("<smile>", Lists.newArrayList("\uD83D\uDE0A", "\uD83D\uDE42", "\uD83D\uDE04"));
        this.emojis.put("<thinking>", Lists.newArrayList("\uD83E\uDD14"));
    }

    @Override
    public Message createMessage(String botName, String conversationId, Long datasetId, Integer idx) {
        Message message = new Message(conversationId, botName);
        Record record = datasetClient.get(datasetId, idx);
        if (record == null)
            return null;

        message.setText(record.getText());
        message.setItemId(record.getId());
        message.setDataItem(true);
        return message;
    }

    @Override
    public Message createMessage(String botName, String conversationId, String messages) {
        String[] possibleMessages = messages.split("\n");
        String text = prepare(pickRandomly(possibleMessages), botName);
        return new Message(conversationId, botName, text);
    }

    private String prepare(String text, String botName) {
        for (String emoji : emojis.keySet()) {
            if (text.contains(emoji)) {
                text = (text.replace(emoji, pickRandomly(emojis.get(emoji))));
            }
        }
        text = text.replace("<name>", botName);
        return text;
    }
}
