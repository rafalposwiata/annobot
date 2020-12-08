package com.annobot.chatbot.model;

import com.annobot.chatbot.MessageFactory;
import com.annobot.chatbot.model.config.DatasetElement;
import com.annobot.chatbot.model.config.Label;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.annobot.chatbot.model.config.UserInputType.LABEL;

public class DatasetState extends ConversationState<DatasetElement> {

    private Long lastItemId = null;
    private Integer lastIdx = 0;

    private boolean validAnnotation = true;

    public DatasetState(DatasetElement element) {
        super(element);
    }

    @Override
    public void receive(Message message) {
        validate(message);

        if (validAnnotation) {
            message.setItemId(lastItemId);
            message.setDatasetId(element.getDatasetId());
            message.setUserInputType(element.getUserInputType());
        } else {
            lastIdx--;
        }
    }

    private void validate(Message message) {
        if (LABEL.equals(element.getUserInputType())) {
            validAnnotation = false;
            for (Label label : element.getLabels()) {
                String text = message.getText();
                if (label.correct(text)) {
                    validAnnotation = true;
                    message.setText(label.getValue());
                    break;
                }
            }
        }
    }

    @Override
    public List<Message> getMessages(String botName, String conversationId, MessageFactory messageFactory) {
        List<Message> messages = new ArrayList<>();
        if (element.stop(lastIdx)) {
            goToNext = true;
            return messages;
        }

        if (!validAnnotation) {
            messages.add(invalidAnnotation(botName, conversationId, messageFactory));
        }

        Message messageBeforeItem = getMessageBeforeItem(botName, conversationId, messageFactory);
        messageBeforeItem = addMessageType(messageBeforeItem);
        if (messageBeforeItem != null)
            messages.add(messageBeforeItem);

        Message messageWithDataItem = messageFactory.createMessage(botName, conversationId, element.getDatasetId(), lastIdx);
        messageWithDataItem = addMessageType(messageWithDataItem);
        messageWithDataItem = completeMessage(messageWithDataItem);
        if (messageWithDataItem == null) {
            goToNext = true;
            return Lists.newArrayList();
        } else {
            lastIdx++;
            lastItemId = messageWithDataItem.getItemId();
            messages.add(messageWithDataItem);
        }

        return messages;
    }

    private Message invalidAnnotation(String botName, String conversationId, MessageFactory messageFactory) {
        Message message = messageFactory.createMessage(botName, conversationId, "Unknown annotation. Try again.");
        return addMessageType(message);
    }

    private Message getMessageBeforeItem(String botName, String conversationId, MessageFactory messageFactory) {
        if (firstMessageWasSend) {
            if (StringUtils.isBlank(element.getAfterEachSample()))
                return null;
            return messageFactory.createMessage(botName, conversationId, element.getAfterEachSample());
        }
        firstMessageWasSend = true;
        return messageFactory.createMessage(botName, conversationId, element.getInstruction());
    }

    private Message completeMessage(Message message) {
        if (message == null) return null;
        message.setUserInputType(element.getUserInputType());
        message.setLabelingSchema(element.getLabelingSchema());
        message.setLabels(element.getLabels());
        return message;
    }
}
