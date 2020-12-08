package com.annobot.chatbot.model;

import com.annobot.chatbot.MessageFactory;
import com.annobot.chatbot.integration.MLModelClient;
import com.annobot.chatbot.model.config.Label;
import com.annobot.chatbot.model.config.LabelingSchema;
import com.annobot.chatbot.model.config.ModelPredictionElement;
import com.annobot.chatbot.model.config.UserInputType;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ModelPredictionState extends ConversationState<ModelPredictionElement> {

    private final MLModelClient mlModelClient;

    private String userText;
    private String lastPrediction;
    private Boolean predictedCorrectly;

    private boolean firstMessage = true;
    private boolean isUserText = true;

    private int count = 0;

    public ModelPredictionState(ModelPredictionElement element, MLModelClient mlModelClient) {
        super(element);
        this.mlModelClient = mlModelClient;
    }

    @Override
    public void receive(Message message) {
        if (isUserText) {
            userText = message.getText();
        } else {
            if (message.getText().contains("y")) {
                message.setUserInputType(UserInputType.TEXT);
                message.setText(lastPrediction);
                predictedCorrectly = true;
            } else {
                predictedCorrectly = false;
            }
        }
    }

    @Override
    public List<Message> getMessages(String botName, String conversationId, MessageFactory messageFactory) {
        List<Message> messages = new ArrayList<>();
        if (firstMessage) {
            Message introductionMessage = messageFactory.createMessage(botName, conversationId,
                    element.getIntroduction());
            introductionMessage = addMessageType(introductionMessage);
            messages.add(introductionMessage);
            firstMessage = false;
        } else if (isUserText) {
            Message messageWithPrediction = messageFactory.createMessage(botName, conversationId,
                    element.getMessageWithModelPrediction());
            String prediction = mlModelClient.predict(element.getModelName(), userText);
            lastPrediction = prediction;
            messageWithPrediction.setText(messageWithPrediction.getText().replace("<prediction>", prediction));
            messageWithPrediction.setBotPrediction(true);
            messageWithPrediction.setUserInputType(UserInputType.LABEL);
            messageWithPrediction.setLabelingSchema(LabelingSchema.BINARY);
            messageWithPrediction.setLabels(Lists.newArrayList(
                    new Label("yes", "y"),
                    new Label("no", "n")));
            messageWithPrediction = addMessageType(messageWithPrediction);

            messages.add(messageWithPrediction);
            isUserText = false;
        } else if (predictedCorrectly != null) {
            Message reaction = messageFactory.createMessage(botName, conversationId,
                    predictedCorrectly ? ":) I'm awesome!" : ":( Maybe next time.");
            messages.add(addMessageType(reaction));
            isUserText = true;
            count++;
        }
        goToNext = element.stop(count);
        return messages;
    }
}
