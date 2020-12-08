package com.annobot.dataset.model;

public class DatasetItemAnnotation {

    private Long datasetItemId;
    private String conversationId;
    private String annotation;

    public DatasetItemAnnotation() {
    }

    public DatasetItemAnnotation(Long datasetItemId, String conversationId, String annotation) {
        this.datasetItemId = datasetItemId;
        this.conversationId = conversationId;
        this.annotation = annotation;
    }

    public Long getDatasetItemId() {
        return datasetItemId;
    }

    public void setDatasetItemId(Long datasetItemId) {
        this.datasetItemId = datasetItemId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
}
