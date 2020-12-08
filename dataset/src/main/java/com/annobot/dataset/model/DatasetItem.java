package com.annobot.dataset.model;

import java.util.List;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

public class DatasetItem {

    private Long datasetItemId;
    private Long datasetId;
    private String content;
    private String goldLabel;
    private List<DatasetItemAnnotation> annotations;

    public DatasetItem(Long datasetItemId, Long datasetId, String content, String goldLabel,
                       List<DatasetItemAnnotation> annotations) {
        this.datasetItemId = datasetItemId;
        this.datasetId = datasetId;
        this.content = content;
        this.goldLabel = goldLabel;
        this.annotations = annotations;
    }

    public String toCSV(){
        String annotations = getAnnotations()
                .stream()
                .map(DatasetItemAnnotation::getAnnotation)
                .collect(joining(","));

        return join(";", content, goldLabel, "[" + annotations + ']');
    }

    public boolean hasAnnotations() {
        return !annotations.isEmpty();
    }

    public Long getDatasetItemId() {
        return datasetItemId;
    }

    public void setDatasetItemId(Long datasetItemId) {
        this.datasetItemId = datasetItemId;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoldLabel() {
        return goldLabel;
    }

    public void setGoldLabel(String goldLabel) {
        this.goldLabel = goldLabel;
    }

    public List<DatasetItemAnnotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<DatasetItemAnnotation> annotations) {
        this.annotations = annotations;
    }
}
