package com.annobot.dataset.persistence;

import javax.persistence.*;

@Entity(name = "dataset_item_annotation")
@Table(schema = "dataset")
public class DatasetItemAnnotationEntity {

    private final static String DATASET_ITEM_ANNOTATION_SEQ =
            "dataset.dataset_item_annotation_dataset_item_annotation_id_seq";

    @Id
    @SequenceGenerator(name = DATASET_ITEM_ANNOTATION_SEQ, sequenceName = DATASET_ITEM_ANNOTATION_SEQ, allocationSize = 1)
    @GeneratedValue(generator = DATASET_ITEM_ANNOTATION_SEQ)
    @Column(name = "dataset_item_annotation_id")
    private Long datasetItemAnnotationId;

    @Column(name = "dataset_id")
    private Long datasetId;

    @Column(name = "dataset_item_id")
    private Long datasetItemId;

    @Column(name = "conversation_id")
    private String conversationId;

    @Column(name = "user_input_type")
    private String userInputType;

    @Column(name = "user_input")
    private String userInput;

    public DatasetItemAnnotationEntity() {
    }

    public DatasetItemAnnotationEntity(Long datasetId, Long datasetItemId, String conversationId, String userInputType,
                                       String userInput) {
        this.datasetId = datasetId;
        this.datasetItemId = datasetItemId;
        this.conversationId = conversationId;
        this.userInputType = userInputType;
        this.userInput = userInput;
    }

    public Long getDatasetItemAnnotationId() {
        return datasetItemAnnotationId;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public Long getDatasetItemId() {
        return datasetItemId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getUserInputType() {
        return userInputType;
    }

    public String getUserInput() {
        return userInput;
    }
}
