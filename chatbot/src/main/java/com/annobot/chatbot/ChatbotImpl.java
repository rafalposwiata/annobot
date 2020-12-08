package com.annobot.chatbot;

import com.annobot.chatbot.integration.DatasetClient;
import com.annobot.chatbot.integration.MLModelClient;
import com.annobot.chatbot.integration.MLModelFactory;
import com.annobot.chatbot.integration.StatisticsClient;
import com.annobot.chatbot.model.Conversation;
import com.annobot.chatbot.model.Message;
import com.annobot.chatbot.model.config.ChatbotConfiguration;
import com.annobot.chatbot.persistence.MessageRepository;
import com.annobot.chatbot.validation.MessageValidator;
import com.annobot.chatbot.validation.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatbotImpl implements Chatbot {

    private final MessageFactory messageFactory;
    private final MessageRepository messageRepository;
    private final StatisticsClient statisticsClient;
    private final ChatbotService chatbotService;
    private final Validator validator;
    private final DatasetClient datasetClient;
    private final MLModelFactory mlModelFactory;

    private Map<String, Conversation> conversations;

    public ChatbotImpl(MessageFactory messageFactory, MessageRepository messageRepository,
                       StatisticsClient statisticsClient, Validator validator, ChatbotService chatbotService,
                       DatasetClient datasetClient, MLModelFactory mlModelFactory) {
        this.messageFactory = messageFactory;
        this.messageRepository = messageRepository;
        this.statisticsClient = statisticsClient;
        this.validator = validator;
        this.datasetClient = datasetClient;
        this.conversations = new HashMap<>();
        this.chatbotService = chatbotService;
        this.mlModelFactory = mlModelFactory;
    }

    @Override
    public List<Message> init(String botName, String user, boolean mobile) {
        String conversationId = user + "_" + botName;
        ChatbotConfiguration config = chatbotService.getConfig(botName);
        MLModelClient mlModelClient = mlModelFactory.create(config.getMlModelUrl());
        Conversation conversation = new Conversation(conversationId, botName, config.getConversationSchema(),
                messageFactory, validator, mlModelClient);
        conversations.put(conversationId, conversation);
        return getMessage(conversation);
    }

    @Override
    public List<Message> receiveMessage(Message message) {
        Conversation conversation = getConversation(message.getConversationId());
//        sendStats(conversation, message.getConversationId());

        conversation.receive(message);
        if (message.isAnnotation()) {
            datasetClient.saveAnnotation(message);
        }
        messageRepository.save(message);

        return getMessage(conversation);
    }

    @Override
    public boolean conversationIsActive(String botName, String user) {
        return conversations.containsKey(user + "_" + botName);
    }

    private List<Message> getMessage(Conversation conversation) {
        List<Message> messages = conversation.getMessages();
        messages.forEach(messageRepository::save);
        return messages;
    }

//    private void sendStats(Conversation conversation, String conversationId) {
//        Message message = conversation.getLastMessage();
//        if (message != null && message.isDataItem()) {
//            double diffTime = (new Date().getTime() - conversation.getLastMessageDate().getTime()) / 1000.0;
//            DecimalFormat df = new DecimalFormat("###.#");
//            statisticsClient.send(conversationId.split("_")[0], message.getItemId(), "annotate",
//                    df.format(diffTime).replace(",", "."),
//                    conversation.getDataset().contains("mobile"));
//        }
//    }

    private String createConversationId() {
        return UUID.randomUUID().toString();
    }

    private Conversation getConversation(String conversationId) {
        Conversation conversation = conversations.get(conversationId);
        if (conversation == null)
            throw new IllegalStateException("Incorrect conversation");
        return conversation;
    }

    private MessageValidator getNumberValidator() {
        return message -> StringUtils.isNumeric(message.getText());
    }

    private MessageValidator getConfirmValidator() {
        return message -> message.getText().toLowerCase().contains("yes");
    }
}
