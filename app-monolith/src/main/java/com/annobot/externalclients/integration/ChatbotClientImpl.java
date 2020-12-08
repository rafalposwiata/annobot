package com.annobot.externalclients.integration;

import com.annobot.chatbot.Chatbot;
import com.annobot.chatbot.ChatbotService;
import com.annobot.chatbot.model.config.externalclients.FacebookConfig;
import com.annobot.externalclients.facebook.model.Button;
import com.annobot.externalclients.facebook.model.Event;
import com.annobot.externalclients.facebook.model.Message;
import com.annobot.externalclients.integration.model.FacebookTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.annobot.chatbot.model.config.UserInputType.LABEL;
import static java.util.stream.Collectors.toList;

@Component("ExternalClientsChatbotClient")
public class ChatbotClientImpl implements ChatbotClient {

    private final ChatbotService chatbotService;
    private final Chatbot chatbot;

    @Autowired
    public ChatbotClientImpl(ChatbotService chatbotService, Chatbot chatbot) {
        this.chatbotService = chatbotService;
        this.chatbot = chatbot;
    }

    @Override
    public FacebookTokens getFacebookTokens(String botName) {
        FacebookConfig config = chatbotService.getConfig(botName).facebookConfig();
        return new FacebookTokens(config.getVerifyToken(), config.getPageAccessToken());
    }

    @Override
    public List<Message> sendToBot(String botName, Event event) {
        String user = event.getSender().getId();
        List<com.annobot.chatbot.model.Message> chatbotMessages;
        if (chatbot.conversationIsActive(botName, user)) {
            com.annobot.chatbot.model.Message message = new com.annobot.chatbot.model.Message();
            message.setBotName(botName);
            message.setText(event.getMessage().getText());
            message.setConversationId(user + "_" + botName);
            message.setUserMessage(true);
            chatbotMessages = chatbot.receiveMessage(message);
        } else {
            chatbotMessages = chatbot.init(botName, user, true);
        }
        return chatbotMessages.stream()
                .map(this::transform)
                .collect(toList());
    }

    private Message transform(com.annobot.chatbot.model.Message message) {
        Message result = new Message();
        result.setText(message.getText());
        if (message.isDataItem() || message.isBotPrediction()) {
            if (LABEL.equals(message.getUserInputType())) {
                Button[] quickReplies = message.getLabels().stream()
                        .map(label ->  new Button().setContentType("text")
                                .setTitle(label.toString())
                                .setPayload(label.getValue()))
                        .toArray(Button[]::new);
                result.setQuickReplies(quickReplies);
            }
        }
        return result;
    }
}
