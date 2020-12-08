package com.annobot.dataset.persistence;

import javax.persistence.*;

@Entity(name = "dataset_item")
@Table(schema = "dataset")
public class DatasetItemEntity {

    private final static String DATASET_ITEM_SEQ = "dataset.dataset_item_dataset_item_id_seq";

    @Id
    @SequenceGenerator(name = DATASET_ITEM_SEQ, sequenceName = DATASET_ITEM_SEQ, allocationSize = 1)
    @GeneratedValue(generator = DATASET_ITEM_SEQ)
    @Column(name = "dataset_item_id")
    private Long datasetItemId;

    @Column(name = "dataset_id")
    private Long datasetId;

    @Column(name = "content")
    private String content;

    public DatasetItemEntity() {
    }

    public DatasetItemEntity(Long datasetId, String content) {
        this.datasetId = datasetId;
        this.content = content;
    }

    public Long getDatasetItemId() {
        return datasetItemId;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public String getContent() {
        return content;
    }
}
