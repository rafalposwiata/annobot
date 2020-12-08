package com.annobot.chatbot.model.config;

import java.util.List;

public class DatasetElement extends ConversationSchemaElement {

    private Long datasetId;
    private UserInputType userInputType;
    private LabelingSchema labelingSchema;
    private List<Label> labels;
    private String instruction;
    private String afterEachSample;
    private Integer numberOfSamples;

    public boolean stop(Integer lastIdx) {
        return numberOfSamples != null && numberOfSamples.equals(lastIdx);
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getAfterEachSample() {
        return afterEachSample;
    }

    public void setAfterEachSample(String afterEachSample) {
        this.afterEachSample = afterEachSample;
    }

    public Integer getNumberOfSamples() {
        return numberOfSamples;
    }

    public void setNumberOfSamples(Integer numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }
}
