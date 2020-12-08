package com.annobot.dataset.persistence;

import javax.persistence.*;

@Entity(name = "dataset_info")
@Table(schema = "dataset")
public class DatasetInfoEntity {

    private final static String DATASET_INFO_SEQ = "dataset.dataset_info_dataset_id_seq";

    @Id
    @SequenceGenerator(name = DATASET_INFO_SEQ, sequenceName = DATASET_INFO_SEQ, allocationSize = 1)
    @GeneratedValue(generator = DATASET_INFO_SEQ)
    @Column(name = "dataset_id")
    private Long datasetId;

    @Column(name = "name")
    private String name;

    public DatasetInfoEntity() {
    }

    public DatasetInfoEntity(String name) {
        this.name = name;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public String getName() {
        return name;
    }
}