package com.annobot.chatbot.model;

import com.annobot.chatbot.model.config.Label;
import com.annobot.chatbot.model.config.LabelingSchema;
import com.annobot.chatbot.model.config.UserInputType;

import java.util.List;

public class Message {

    private String text;
    private String conversationId;
    private String botName;
    private String messageType;
    private Long datasetId;
    private Long itemId;
    private boolean dataItem = false;
    private boolean botPrediction = false;
    private boolean readOnly = false;
    private boolean userMessage = false;
    private UserInputType userInputType;

    private LabelingSchema labelingSchema;
    private List<Label> labels;

    public Message() {
    }

    public Message(String conversationId, String botName) {
        this.conversationId = conversationId;
        this.botName = botName;
    }

    public Message(String conversationId, String botName, String text) {
        this.conversationId = conversationId;
        this.botName = botName;
        this.text = text;
    }

    public boolean isAnnotation() {
        return itemId != null && userMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public boolean isDataItem() {
        return dataItem;
    }

    public void setDataItem(boolean dataItem) {
        this.dataItem = dataItem;
    }

    public boolean isBotPrediction() {
        return botPrediction;
    }

    public void setBotPrediction(boolean botPrediction) {
        this.botPrediction = botPrediction;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public boolean isUserMessage() {
        return userMessage;
    }

    public void setUserMessage(boolean userMessage) {
        this.userMessage = userMessage;
    }

    public UserInputType getUserInputType() {
        return userInputType;
    }

    public void setUserInputType(UserInputType userInputType) {
        this.userInputType = userInputType;
    }

    public LabelingSchema getLabelingSchema() {
        return labelingSchema;
    }

    public void setLabelingSchema(LabelingSchema labelingSchema) {
        this.labelingSchema = labelingSchema;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return text;
    }
}
