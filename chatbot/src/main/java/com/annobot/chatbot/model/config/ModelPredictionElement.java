package com.annobot.chatbot.model.config;

public class ModelPredictionElement extends ConversationSchemaElement {

    private String modelName;
    private Long datasetId;
    private String introduction;
    private String messageWithModelPrediction;
    private Integer numberOfRepetitions;

    public boolean stop(Integer count) {
        return numberOfRepetitions != null && numberOfRepetitions.equals(count);
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMessageWithModelPrediction() {
        return messageWithModelPrediction;
    }

    public void setMessageWithModelPrediction(String messageWithModelPrediction) {
        this.messageWithModelPrediction = messageWithModelPrediction;
    }

    public Integer getNumberOfRepetitions() {
        return numberOfRepetitions;
    }

    public void setNumberOfRepetitions(Integer numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }
}
